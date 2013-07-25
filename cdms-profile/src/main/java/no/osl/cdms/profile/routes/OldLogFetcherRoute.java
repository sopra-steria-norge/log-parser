package no.osl.cdms.profile.routes;

import org.apache.camel.builder.RouteBuilder;
import org.joda.time.DateTime;

public class OldLogFetcherRoute extends RouteBuilder {

    public static final String OLD_LOG_FETCHER_ROUTE_ID = "OldLogFetcherRoute";
    private static final String LOG_DIRECTORY = "data/log"; //"C:/Users/apalfi/Desktop/logs";

    private static final String LOG_FILE_ENDPOINT = "file:%s?include=performance.log\\.\\d{4}-\\d{2}-\\d{2}";

    public static DateTime lastRead;

    @Override
    public void configure() throws Exception{
        lastRead = new DateTime();

        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY).startupOrder(2)
                .split(body().tokenize("\n")).streaming()
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .bean(this, "heartbeat")
                .routeId(OLD_LOG_FETCHER_ROUTE_ID);
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
