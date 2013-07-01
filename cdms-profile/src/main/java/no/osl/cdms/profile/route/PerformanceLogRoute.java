package no.osl.cdms.profile.route;

import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.parser.DatabaseEntryParser;
import no.osl.cdms.profile.parser.LogLineRegexParser;
import org.apache.camel.builder.RouteBuilder;

import java.util.Map;

public class PerformanceLogRoute extends RouteBuilder {

    private static final String LOG_DIRECTORY = "data";
    private static final String LOG_FILE = "performance.log";
    /**
     * Milliseconds between each poll from the log file
     */
    private static final int DELAY = 1000;

    @Override
    public void configure() throws Exception {
        Parser logParser = new LogLineRegexParser(); // TODO should probably be registered in spring.xml
        DatabaseEntryParser dbParser = new DatabaseEntryParser();

        fromF("stream:file?fileName=%s/%s&scanStream=true&scanStreamDelay=%d", LOG_DIRECTORY, LOG_FILE, DELAY)
                .convertBodyTo(String.class)
                .choice().when(body().isGreaterThan(""))
                .bean(logParser, "parse")
                .bean(dbParser, "parse")
                .bean(this, "log");
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
