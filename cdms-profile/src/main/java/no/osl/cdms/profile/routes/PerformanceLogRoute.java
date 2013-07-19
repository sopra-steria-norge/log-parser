package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.factories.EntityFactory;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import no.osl.cdms.profile.parser.LogLineRegexParser;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class PerformanceLogRoute extends RouteBuilder {

    public static final String PERFORMANCE_LOG_ROUTE_ID = "PerformanceLogRoute";
    private static final String LOG_DIRECTORY = "C:/data";
    private static final String LOG_FILE = "performance.log";
    private static final int DELAY = 0;

    private static final String LOG_FILE_ENDPOINT = "stream:file?fileName=%s/%s&scanStream=true&scanStreamDelay=%d";
    private static final String DATABASE_ENDPOINT = "jpa:%s?usePersist=true";

    @Autowired
    private EntityFactory entityFactory;
    private LogLineRegexParser logLineRegexParser = new LogLineRegexParser();

    @Override
    public void configure() throws Exception{
        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY, LOG_FILE, DELAY)
                .convertBodyTo(String.class)                  // Converts input to String
                .choice().when(body().isGreaterThan(""))      // Ignores empty lines
                .bean(logLineRegexParser, "parse")            // Parses log entry into String map
                .bean(entityFactory, "createTimemeasurement") // Parses log entry into database format
                .split(body())
                .choice().when(body().isNotNull())
                .toF(DATABASE_ENDPOINT, body().getClass().toString())
                .routeId(PERFORMANCE_LOG_ROUTE_ID);
    }

    public String toString() {
        return PERFORMANCE_LOG_ROUTE_ID;
    }
}
