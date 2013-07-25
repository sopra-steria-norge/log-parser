package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.interfaces.EntityFactory;
import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityParserRoute extends RouteBuilder {

    public static final String ENTITY_PARSER_ROUTE_ID = "EntityParserRoute";

    public static final String INPUT_ENDPOINT = "direct:entityParserRoute";
    private static final String DATABASE_ENDPOINT = "jpa:%s?usePersist=true";

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private Parser parser;

    @Autowired
    private GuavaHelpers guavaHelpers;

    public EntityParserRoute() {

    }

//    public EntityParserRoute(EntityFactory entityFactory, LogLineRegexParser logLineRegexParser) {
//        this.entityFactory = entityFactory;
//        this.logLineRegexParser = logLineRegexParser;
//    }


    @Override
    public void configure() throws Exception{
//        DateTime firstToRead = new DateTime().minusDays(14);
//        TimeMeasurement oldestTimeMeasurement = logRepository.getOldestTimeMeasurement();
//        if (oldestTimeMeasurement != null) {
//            DateTime oldest = oldestTimeMeasurement.getJodaTimestamp();
//            if (oldest.isAfter(firstToRead)) {
//                firstToRead = oldest;
//            }
//        }

        //from("file//:data/log").bean().choice(body().isNotNull()).

        //String firstToReadTimestamp = firstToRead.getYear()+"-"+firstToRead.getMonthOfYear()+"-"+firstToRead.getDayOfMonth();


        //"performance.log"+ firstToReadTimestamp;

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
        final TimeMeasurement lastInsertedTimeMeasurement = logRepository.getLatestTimeMeasurement();
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

    public String toString() {
        return ENTITY_PARSER_ROUTE_ID;
    }
}
