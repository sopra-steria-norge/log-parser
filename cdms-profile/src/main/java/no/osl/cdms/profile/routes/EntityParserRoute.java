package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.interfaces.EntityFactory;
import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.persistence.LogRepository;
import no.osl.cdms.profile.persistence.TimeMeasurementEntity;
import no.osl.cdms.profile.utilities.EntityFactoryHelpers;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityParserRoute extends RouteBuilder {

    private static final String ENTITY_PARSER_ROUTE_ID = "EntityParserRoute";

    public static final String INPUT_ENDPOINT = "direct:entityParserRoute";
    private static final String DATABASE_ENDPOINT = "jpa:%s?usePersist=true";
    public static String routeId() {
        return ENTITY_PARSER_ROUTE_ID;
    }

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private Parser parser;

    @Autowired
    private EntityFactoryHelpers guavaHelpers;

    private TimeMeasurement lastInsertedTimeMeasurement;

    public EntityParserRoute() {

    }

    @Override
    public void configure() throws Exception{
        lastInsertedTimeMeasurement = logRepository.getLatestTimeMeasurement();
        from(INPUT_ENDPOINT).startupOrder(1)
                .choice().when(isUnreadLine())
                .bean(parser, "process")            // Parses log entry into String map
                .bean(entityFactory, "process") // Parses log entry into database format
                .split(body())
                .choice().when(body().isNotNull())
                .toF(DATABASE_ENDPOINT, TimeMeasurementEntity.class.getCanonicalName())
                .routeId(ENTITY_PARSER_ROUTE_ID);
    }

    private Predicate isUnreadLine() {
        return new Predicate() {
            @Override
            public boolean matches(Exchange exchange) {
                DateTime logEntryDate = new DateTime(guavaHelpers.parseDateString(exchange.getIn().getBody().toString().substring(0,23)));
                if (lastInsertedTimeMeasurement == null) {
                    return true;
                } else if (logEntryDate.isAfter(lastInsertedTimeMeasurement.getJodaTimestamp()) ||
                    logEntryDate.isEqual(lastInsertedTimeMeasurement.getJodaTimestamp())) {
                    return true;
                }
                return false;
            }
        };
    }
    
    @Override
    public String toString() {
        return ENTITY_PARSER_ROUTE_ID;
    }
}
