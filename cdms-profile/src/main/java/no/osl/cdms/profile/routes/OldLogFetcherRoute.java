package no.osl.cdms.profile.routes;

import org.apache.camel.builder.RouteBuilder;

public class OldLogFetcherRoute extends RouteBuilder {

    private static final String OLD_LOG_FETCHER_ROUTE_ID = "OldLogFetcherRoute";
    private static final String LOG_DIRECTORY = "data/log"; //"C:/Users/apalfi/Desktop/logs";

    private static final String LOG_FILE_ENDPOINT = "file:%s?include=performance.log\\.\\d{4}-\\d{2}-\\d{2}";

    @Override
    public void configure() throws Exception{

        fromF(LOG_FILE_ENDPOINT, LOG_DIRECTORY).startupOrder(2)
                .split(body().tokenize("\n")).streaming()
                .to(EntityParserRoute.INPUT_ENDPOINT)
                .routeId(OLD_LOG_FETCHER_ROUTE_ID);
    }

    public String toString() {
        return OLD_LOG_FETCHER_ROUTE_ID;
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
