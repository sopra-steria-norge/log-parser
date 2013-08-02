/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nutgaard
 */
public class JMXQueueRoute extends RouteBuilder {
    private static final Logger logger = Logger.getLogger(DeleteRoute.class);

    private static final String JMXQueueRoute = "JMXQueueRoute";

    @Override
    public void configure() throws Exception {
        String domain = "localhost:1099";   
        String objectname = "no.osl.cdms.profile.services.jmx:name=jmxServiceBean,type=JMXService";
        String attribute = "ICW";
       from("jmx:platform?objectDomain="+domain+"&objectName="+objectname+"&" + 
     "monitorType=counter&observedAttribute="+attribute)
               .transacted()
               .bean(this, "updateQueue")
               .routeId(JMXQueueRoute);
    }
    @Override
    public String toString() {
        return JMXQueueRoute;
    }

    public static String routeId() {
        return JMXQueueRoute;
    }
    @Transactional
    public void updateQueue(Object o) {
        logger.error("UpdateQueue:: "+o);
    }
}
