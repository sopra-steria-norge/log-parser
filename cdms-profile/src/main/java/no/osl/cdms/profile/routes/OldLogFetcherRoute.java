package no.osl.cdms.profile.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;

public class OldLogFetcherRoute extends RouteBuilder {

    public static final String OLD_LOG_FETCHER_ROUTE_ID = "OldLogFetcherRoute";
    private static final String LOG_DIRECTORY = "data/log"; //"C:/Users/apalfi/Desktop/logs";

    private static final String LOG_FILE_ENDPOINT = "file:%s?include=performance.log\\.\\d{4}-\\d{2}-\\d{2}";

    public static DateTime lastRead;

    @Override
    public void configure() throws Exception{
        lastRead = new DateTime();

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
                   String fileName = ((GenericFile)exchange.getIn().getBody()).getFileName();
                   String fileDate = fileName.substring("performance.log.".length());
                   DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
                   DateTime date = dtf.parseDateTime(fileDate);
                   return new DateTime().minusDays(15).isBefore(date);
               }
           };
    }

    public String toString() {
        return OLD_LOG_FETCHER_ROUTE_ID;
    }

    public void heartbeat() {
        lastRead = new DateTime();
    }
//
//    public static void main(String[] a) throws Exception {
//        OldLogFetcherRoute route = new OldLogFetcherRoute();
//        route.getContext().addRoutes(route);
//        route.getContext().start();
////        System.out.println(route.getContext().getRouteDefinition("OldLogFetcherRoute").getStatus(route.getContext()).isSuspendable());
//
//        Thread.sleep(10000);
//
//    }
}
