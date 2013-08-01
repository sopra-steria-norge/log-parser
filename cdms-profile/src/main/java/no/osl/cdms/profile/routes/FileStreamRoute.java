package no.osl.cdms.profile.routes;


import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import no.osl.cdms.profile.routes.components.RouteExceptionHandler;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class FileStreamRoute extends RouteBuilder {

    // Reading of file
    private static final String FILE_STREAM_ROUTE_ID = "FileStreamRoute";
    private static final String TIMER_ENDPOINT = "timer://%s?period=%d&delay=%d";
    private static final String LOG_DIRECTORY = "data/log";
    private static final String LOG_FILE = "performance.log";
    private static final int INITIAL_DELAY = 0;
    private static final int PERIOD = 10000;
    private long fileOffset = 0;
    private final long timeoutThreshold = 10000; 

    // Polling of OldLogFetcherRoute
    private static final long LOG_FETCHER_POLLING_DELAY = 10000;
    private static final long LOG_FETCHER_NO_MORE_FILES_TIMEOUT = 10000;
    private Timer pollingTimer;
    private long timeout;
    private Logger logger = Logger.getLogger(FileStreamRoute.class);

    @Autowired
    private RouteExceptionHandler exceptionHandler;

    public FileStreamRoute() {
        super();
        waitForOldLogFetcherRoute();
    }

    @Override
    public void configure() throws Exception {
        onException(Exception.class).process(exceptionHandler).markRollbackOnly().handled(true);
        fromF(TIMER_ENDPOINT, this.getClass().getSimpleName(), PERIOD, INITIAL_DELAY).autoStartup(false)
                .bean(this, "readLines")
                .split(body())
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .routeId(FILE_STREAM_ROUTE_ID);
    }

    public List<String> readLines() throws IOException {
        this.timeout = System.currentTimeMillis();
        List<String> lines = new LinkedList<String>();
        String line;

        RandomAccessFile file = new RandomAccessFile(LOG_DIRECTORY + "/" + LOG_FILE, "r");
        if (file.length() < fileOffset) {
            logger.warn("Detected file change, resetting fileOffset");
            fileOffset = 0;
        }
        file.seek(fileOffset);

        while ((line=file.readLine()) != null && !isTimeout()) {
            Thread.yield();
            if (line.length() > 0) {
                lines.add(line);
            }
        }
        fileOffset = file.getFilePointer();
        file.close();
        return lines;
    }

    /**
     * Waits for OldLogFetcherRoute to complete, and then starts this route.
     */
    private void waitForOldLogFetcherRoute() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    DateTime now = new DateTime();
                    long difference = now.getMillis() - OldLogFetcherRoute.lastReadDate().getMillis();
                    if (difference < LOG_FETCHER_NO_MORE_FILES_TIMEOUT) {
                        return;
                    }

                    startRoute();
                } catch (Exception e) {
                    logger.error("FileStreamRoute::" + e.getMessage());
                }
            }
        };

        pollingTimer = new Timer();
        pollingTimer.schedule(timerTask, LOG_FETCHER_POLLING_DELAY, LOG_FETCHER_POLLING_DELAY);
    }

    private void startRoute() throws Exception {
        pollingTimer.cancel();
        getContext().startRoute(FILE_STREAM_ROUTE_ID);
        getContext().stopRoute(OldLogFetcherRoute.routeId());
    }

    @Override
    public String toString() {
        return FILE_STREAM_ROUTE_ID;
    }

    public static String routeId() {
        return FILE_STREAM_ROUTE_ID;
    }

    private boolean isTimeout() {
        return (System.currentTimeMillis() - timeout) > timeoutThreshold;
    }
}

