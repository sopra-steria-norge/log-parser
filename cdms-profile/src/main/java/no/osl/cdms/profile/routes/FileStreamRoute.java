package no.osl.cdms.profile.routes;


import java.util.Timer;
import java.util.TimerTask;
import javax.ws.rs.HEAD;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class FileStreamRoute extends RouteBuilder {
    private static final String FILE_STREAM_ROUTE_ID = "FileStreamRoute";
    private static final String LOG_DIRECTORY = "data/log";
    private static final String LOG_FILE = "performance.log";
    private static final int DELAY = 0;
    private static final long POLLING_DELAY = 10000;
    private static final long NO_MORE_FILES_TIMEOUT = 10000;
    private static final String LOG_FILE_ENDPOINT = "stream:file?fileName=%s/%s&scanStream=true&scanStreamDelay=%d";
    public static String routeId() {
        return FILE_STREAM_ROUTE_ID;
    }

    private Logger logger = Logger.getLogger(FileStreamRoute.class);

    private Timer pollingTimer;

    public FileStreamRoute() {
        super();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    DateTime now = new DateTime();
                    long difference = now.getMillis() - OldLogFetcherRoute.lastReadDate().getMillis();
                    if (difference < NO_MORE_FILES_TIMEOUT) {
                        return;
                    }

                    startRoute();
                } catch (Exception e) {
                    logger.error("FileStreamRoute::" + e.getMessage());
                }
            }
        };

        pollingTimer = new Timer();
        pollingTimer.schedule(timerTask, POLLING_DELAY, POLLING_DELAY);

    }

    private void startRoute() throws Exception {
        pollingTimer.cancel();
        getContext().startRoute(FILE_STREAM_ROUTE_ID);
        getContext().stopRoute(OldLogFetcherRoute.routeId());
    }

    @Override
    public void configure() throws Exception {
        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY, LOG_FILE, DELAY).autoStartup(false)
                .convertBodyTo(String.class) // Converts input to String
                .choice().when(body().isGreaterThan("")) // Ignores empty lines
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .routeId(FILE_STREAM_ROUTE_ID);
    }

    @Override
    public String toString() {
        return FILE_STREAM_ROUTE_ID;
    }
}
