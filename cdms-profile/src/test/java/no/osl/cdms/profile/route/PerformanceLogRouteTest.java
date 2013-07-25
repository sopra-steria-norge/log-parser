package no.osl.cdms.profile.route;

//package no.osl.cdms.profile.routes;
//import no.osl.cdms.profile.api.TimeMeasurement;
//import no.osl.cdms.profile.factories.EntityFactory;
//import no.osl.cdms.profile.parser.LogLineRegexParser;
//import org.apache.camel.builder.AdviceWithRouteBuilder;
//import org.apache.camel.component.mock.MockEndpoint;
//import org.apache.camel.test.spring.CamelSpringTestSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import static no.osl.cdms.profile.routes.PerformanceLogRoute.PERFORMANCE_LOG_ROUTE_ID;
//
//public class PerformanceLogRouteTest extends CamelSpringTestSupport {
//
//    PerformanceLogRoute performanceLogRoute;
//    LogLineRegexParser logLineRegexParser;
//    EntityFactory entityFactory;
//
//    private final String LOG_LINE = "I am a log line";
//    private Map<String, String> parsedLogLine;
//
//    @Before
//    public void setup() throws Exception {
//
//        // Mocks up resources
//        entityFactory = mock(EntityFactory.class);
//        logLineRegexParser = mock(LogLineRegexParser.class);
//
//
//
//        // Creates and starts route
//        performanceLogRoute = new PerformanceLogRoute(entityFactory, logLineRegexParser);
//
//        context.addRoutes(performanceLogRoute);
//        template.setDefaultEndpointUri("direct:start");
//        context.getRouteDefinition(PERFORMANCE_LOG_ROUTE_ID).adviceWith(context, new AdviceWithRouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                replaceFromWith("direct:start");
//                weaveAddLast().to("mock:result");
//            }
//        });
//        context.start();
//    }
//
//    @Test
//    public void testNull() throws Exception {
//        MockEndpoint to = getMockEndpoint("mock:result");
//        template.send("direct:start", createExchangeWithBody(""));
//        to.expectedMessageCount(0);
//    }
//
//    @Test
//    public void testBeansAreCalled() throws Exception {
//        parsedLogLine = new HashMap<String, String>();
//        parsedLogLine.put("Timestamp", "123");
//        parsedLogLine.put("Mock data", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
//        when(logLineRegexParser.parse(LOG_LINE)).thenReturn(parsedLogLine);
//
//        List<TimeMeasurement> timeMeasurements = new ArrayList<TimeMeasurement>();
//        when(entityFactory.createTimemeasurement(parsedLogLine)).thenReturn(timeMeasurements);
//
//        template.send("direct:start", createExchangeWithBody(LOG_LINE));
//        verify(logLineRegexParser).parse(LOG_LINE);
//        verify(entityFactory).createTimemeasurement(parsedLogLine);
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
//                "classpath:test-cdms-profile-infra-ctx.xml", "classpath:test-cdms-profile-camel-ctx.xml");
//    }
//}
