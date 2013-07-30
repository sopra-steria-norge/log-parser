package no.osl.cdms.profile.routes.components;

import no.osl.cdms.profile.routes.DeleteRoute;
import no.osl.cdms.profile.routes.EntityParserRoute;
import no.osl.cdms.profile.routes.FileStreamRoute;
import no.osl.cdms.profile.routes.OldLogFetcherRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RouteExceptionHandler implements Processor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.error("Camel route exception caught, stopping all camel routes.");
        CamelContext context = exchange.getContext();
        context.stopRoute(FileStreamRoute.routeId());
        context.stopRoute(DeleteRoute.routeId());
        context.stopRoute(EntityParserRoute.routeId());
        context.stopRoute(OldLogFetcherRoute.routeId());
        context.stop();
    }
}
