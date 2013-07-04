package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.parser.DatabaseEntryParser;
import no.osl.cdms.profile.parser.LogLineRegexParser;
import no.osl.cdms.profile.routes.logentryupdate.LogEntryUpdateProcessor;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;

public class PerformanceLogRoute extends RouteBuilder {

    public static final String PERFORMANCE_LOG_ROUTE_ID = "PerformanceLogRoute";
    private static final String LOG_DIRECTORY = "C:/data";
    private static final String LOG_FILE = "performance.log";
    private static final int DELAY = 0;

    private LogLineRegexParser logLineRegexParser = new LogLineRegexParser();
    private DatabaseEntryParser databaseEntryParser = new DatabaseEntryParser();
    private LogEntryUpdateProcessor logEntryUpdateProcessor = new LogEntryUpdateProcessor();

    private static final String LOG_FILE_ENDPOINT = "stream:file?fileName="+LOG_DIRECTORY +"/"+LOG_FILE+"&scanStream=true&scanStreamDelay=" + DELAY;

    @Override
    public void configure() throws Exception{

        //onException(Exception.class)/*.process(exceptionHandler)*/.markRollbackOnly().handled(true);
        from(LOG_FILE_ENDPOINT)
                .convertBodyTo(String.class)                // Converts input to String
                .choice().when(body().isGreaterThan(""))    // Ignores empty lines
                .bean(logLineRegexParser, "parse")          // Parses log entry into String map
                .bean(databaseEntryParser)                  // Parses log entry into database format
                .bean(logEntryUpdateProcessor)              // Adds log entry to database
                .routeId(PERFORMANCE_LOG_ROUTE_ID);
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
