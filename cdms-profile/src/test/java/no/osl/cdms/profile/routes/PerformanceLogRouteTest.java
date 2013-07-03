package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.route.PerformanceLogRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;
import org.junit.Test;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
public class PerformanceLogRouteTest /*extends CamelSpringTestSupport*/ {

    PerformanceLogRoute performanceLogRoute = new PerformanceLogRoute();

    @Before
    public void setup() throws Exception {
        CamelContext context = new DefaultCamelContext(); // ikke nødvendig ved arv av CamelSpringTestSupport
        performanceLogRoute = new PerformanceLogRoute();
        context.setTracing(true);
        context.addRoutes(performanceLogRoute);
        context.start();

    }

    @Test
    public void routeTest() {

        assert(true);

    }

    /*@Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:META-INF/spring/cdms-profile-ctx.xml",
                "classpath:test-cdms-profile-infra-ctx.xml");
    }*/
}
