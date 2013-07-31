package no.osl.cdms.profile.routes;

import java.util.logging.Logger;

import no.osl.cdms.profile.routes.components.RouteExceptionHandler;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

public class OldLogFetcherRoute extends RouteBuilder {

    private static final String OLD_LOG_FETCHER_ROUTE_ID = "OldLogFetcherRoute";
    private static final String LOG_DIRECTORY = "data/log";

    private static final String LOG_FILE_ENDPOINT = "file:%s?include=performance.log\\.\\d{4}-\\d{2}-\\d{2}&noop=true";

    private static DateTime lastRead;

    @Autowired
    private RouteExceptionHandler exceptionHandler;

    @Override
    public void configure() throws Exception {
        heartbeat();

        onException(Exception.class).process(exceptionHandler).markRollbackOnly().handled(true);
        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY).startupOrder(2)
                .choice().when(shouldRead())
                .split(body().tokenize("\n")).streaming()
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .bean(this, "heartbeat")
                .routeId(OLD_LOG_FETCHER_ROUTE_ID);
    }

    private Predicate shouldRead() {
        return new Predicate() {
            @Override
            public boolean matches(Exchange exchange) {
                String fileName = ((GenericFile) exchange.getIn().getBody()).getFileName();
                String fileDate = fileName.substring("performance.log.".length());
                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime date = dtf.parseDateTime(fileDate);
                return new DateTime().minusDays(15).isBefore(date);
            }
        };
    }

    @Override
    public String toString() {
        return OLD_LOG_FETCHER_ROUTE_ID;
    }

    public void heartbeat() {
        lastRead = new DateTime();
    }

    public static DateTime lastReadDate() {
        return lastRead;
    }

    public static String routeId() {
        return OLD_LOG_FETCHER_ROUTE_ID;
    }
    private static final Logger logger = Logger.getLogger(OldLogFetcherRoute.class.getName());

}
