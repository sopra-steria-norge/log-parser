package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.factories.EntityFactory;
import no.osl.cdms.profile.log.LogRepository;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
//        "classpath:test-cdms-profile-infra-ctx.xml"})
public class PerformanceLogRouteTest extends CamelSpringTestSupport {

    PerformanceLogRoute performanceLogRoute;

//    @Autowired
//    private LogRepository logRepository;

    @Before
    public void setup() throws Exception {
        //performanceLogRoute = new PerformanceLogRoute();
        //performanceLogRoute.setLogRepository(mock(LogRepository.class));
        //performanceLogRoute.setLogRepository(logRepository);
        //context.setTracing(true);
        //context.addRoutes(performanceLogRoute);
        //context.start();
    }

    @Test
    public void routeTest() throws Exception {
        //Thread.sleep(1000);
        //assert(true);
    }

    @After
    public void tearDown() throws Exception {
        //context.stop();
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:test-cdms-profile-camel-ctx.xml",
                "classpath:test-cdms-profile-infra-ctx.xml");
    }
}
