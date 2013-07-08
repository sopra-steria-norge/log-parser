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

    private LogLineRegexParser logLineRegexParser = new LogLineRegexParser();
    private EntityFactory entityFactory = EntityFactory.getInstance();

    private static final String LOG_FILE_ENDPOINT = "stream:file?fileName="+LOG_DIRECTORY +"/"+LOG_FILE+"&scanStream=true&scanStreamDelay=" + DELAY;

    @Autowired
    private LogRepository logRepository;

    @Override
    public void configure() throws Exception{
        from(LOG_FILE_ENDPOINT)
                .convertBodyTo(String.class)                  // Converts input to String
                .choice().when(body().isGreaterThan(""))      // Ignores empty lines
                .bean(logLineRegexParser, "parse")            // Parses log entry into String map
                .bean(entityFactory, "createTimemeasurement") // Parses log entry into database format
                .split(body())
                .choice().when(body().isNotNull())
                .to("jpa:" + body().getClass().toString() + "?usePersist=true")
                .routeId(PERFORMANCE_LOG_ROUTE_ID);
    }

    public void setLogRepository(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public String toString() {
        return PERFORMANCE_LOG_ROUTE_ID;
    }
}
