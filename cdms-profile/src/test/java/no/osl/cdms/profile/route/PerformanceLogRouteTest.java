package no.osl.cdms.profile.route;

import org.junit.Before;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PerformanceLogRouteTest extends CamelSpringTestSupport {


    @Before
    public void setup() throws Exception {
        context.setTracing(true);
        context.addRoutes(new PerformanceLogRoute());
        context.start();
    }

    public void routeTest() {
        assert(true);

    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        //return new ClassPathXmlApplicationContext("classpath:test-cdms-core-camel-ctx.xml","classpath:test-cdms-core-infra-ctx.xml");
        return null; // TODO
    }
}
