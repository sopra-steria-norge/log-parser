package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.parser.DatabaseEntryParser;
import no.osl.cdms.profile.parser.LogLineRegexParser;
import no.osl.cdms.profile.routes.logentryupdate.LogEntryUpdateProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class PerformanceLogRoute extends RouteBuilder {

    public static final String PERFORMANCE_LOG_ROUTE = "PerformanceLogRoute";
    private static final String LOG_DIRECTORY = "data";
    private static final String LOG_FILE = "performance.log";
    /**
     * Milliseconds between each poll from the log file
     */
    private static final int DELAY = 0;

    @Autowired
    Parser logLineRegexParser;

    @Autowired
    DatabaseEntryParser databaseEntryParser;

    @Autowired
    private LogEntryUpdateProcessor logEntryUpdateProcessor;

    @Override
    public void configure() throws Exception{

        //onException(Exception.class)/*.process(exceptionHandler)*/.markRollbackOnly().handled(true);

        Parser logLineRegexParser = new LogLineRegexParser();
        DatabaseEntryParser databaseEntryParser = new DatabaseEntryParser();
        fromF("stream:file?fileName=%s/%s&scanStream=true&scanStreamDelay=%d", LOG_DIRECTORY, LOG_FILE, DELAY)
                .convertBodyTo(String.class)                // Converts input to String
                .choice().when(body().isGreaterThan(""))    // Ignores empty lines
                .bean(logLineRegexParser, "parse")          // Parses log entry into String map
                .bean(databaseEntryParser, "parse")         // Parses log entry into database format
                .bean(logEntryUpdateProcessor)              // Adds log entry to database
                .routeId(PERFORMANCE_LOG_ROUTE);
    }

    /**
     * Test method for viewing parsed log entry
     * Can safely be deleted
     * @param log
     */
    private void printLogEntry(Map<String, String> log) {
        for (String key: log.keySet()) {
            System.out.printf("%s\t\t%s\n", key, log.get(key));
        }
        System.out.println();
    }
}
