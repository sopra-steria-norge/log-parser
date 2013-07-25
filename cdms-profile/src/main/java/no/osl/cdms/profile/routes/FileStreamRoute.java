package no.osl.cdms.profile.routes;

import org.apache.camel.builder.RouteBuilder;

public class FileStreamRoute extends RouteBuilder {

    public static final String FILE_STREAM_ROUTE_ID = "FileStreamRoute";
    private static final String LOG_DIRECTORY = "data/log";
    private static final String LOG_FILE = "performance.log";
    private static final int DELAY = 0;

    private static final String LOG_FILE_ENDPOINT = "stream:file?fileName=%s/%s&scanStream=true&scanStreamDelay=%d";

    @Override
    public void configure() throws Exception {
        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY, LOG_FILE, DELAY).autoStartup(false)
                .convertBodyTo(String.class)                  // Converts input to String
                .choice().when(body().isGreaterThan(""))      // Ignores empty lines
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .routeId(FILE_STREAM_ROUTE_ID);
    }

    @Override
    public String toString() {
        return FILE_STREAM_ROUTE_ID;
    }
}
