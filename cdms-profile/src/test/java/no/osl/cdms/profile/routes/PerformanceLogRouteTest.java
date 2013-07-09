package no.osl.cdms.profile.routes;

import no.osl.cdms.profile.log.LogRepository;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

import static org.mockito.Mockito.mock;

public class PerformanceLogRouteTest extends CamelSpringTestSupport {

    PerformanceLogRoute performanceLogRoute;

    /*@Autowired
    LogRepository logRepository;*/


    @Before
    @Rollback(false)
    public void setup() throws Exception {
        //performanceLogRoute = new PerformanceLogRoute();
        //performanceLogRoute.setLogRepository(mock(LogRepository.class));
        //performanceLogRoute.setLogRepository(logRepository);
        //context.setTracing(true);
        //context.addRoutes(performanceLogRoute);
        //context.start();
    }

    @Test
    @Rollback(false)
    public void routeTest() throws Exception {
        //Thread.sleep(1000);
        //assert(true);
    }

    @After
    @Rollback(false)
    public void tearDown() throws Exception {
        //context.stop();
    }

    @Override
    protected AbstractApplicationContext createApplicationContext() {
        return new ClassPathXmlApplicationContext("classpath:test-cdms-profile-camel-ctx.xml",
                "classpath:test-cdms-profile-infra-ctx.xml");
    }
}
