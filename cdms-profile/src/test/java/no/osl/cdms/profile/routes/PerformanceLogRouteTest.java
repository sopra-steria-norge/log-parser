//package no.osl.cdms.profile.routes;
//import org.apache.camel.test.spring.CamelSpringTestSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
////        "classpath:test-cdms-profile-infra-ctx.xml"})
//public class PerformanceLogRouteTest extends CamelSpringTestSupport {
//
//    PerformanceLogRoute performanceLogRoute;
//
////    @Autowired
////    private LogRepository logRepository;
//
//    @Before
//    public void setup() throws Exception {
//        performanceLogRoute = new PerformanceLogRoute();
//        context.setTracing(true);
//        context.addRoutes(performanceLogRoute);
//        context.start();
//    }
//
//    @Test
//    public void routeTest() throws Exception {
//        Thread.sleep(1000);
//        assert(true);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        context.stop();
//    }
//
//    @Override
//    protected AbstractApplicationContext createApplicationContext() {
//        return new ClassPathXmlApplicationContext(
//                "classpath:test-cdms-profile-infra-ctx.xml");
//    }
//}
