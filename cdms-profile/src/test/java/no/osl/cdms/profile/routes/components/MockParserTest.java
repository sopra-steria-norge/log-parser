package no.osl.cdms.profile.routes.components;

import java.util.Map;

import no.osl.cdms.profile.interfaces.Parser;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MockParserTest {
    private static final Logger logger = Logger.getLogger(MockParserTest.class);
    
    private Parser parser;
    public MockParserTest() {
    }

    @Before
    public void setup() {
        parser = new ParserImpl();
    }
    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void localMockLogLine() {
        logger.info("localMockLogLine");
        String logline = "2013-06-25 15:02:08,876 LocalThreadContext[id=value1,duration=ObjName2[key1=value1,key2=value2,key3=ObjName3[key1=value1]]]";
        Map<String, String> result = parser.process(logline);

        String[][] validate = new String[][]{
            {"timestamp", "2013-06-25 15:02:08,876"},
            {"LocalThreadContext.id", "value1"},
            {"LocalThreadContext.duration", "ObjName2"}
        };
        for (String[] entry : validate) {
            assertEquals(entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }

    @Test
    public void multiMockLogLine() {
        logger.info("multiMockLogLine");
        String obj1 = "Simple{key1=value1},";
        String obj2 = "Hard{componded.name.on:key=1231A..SD},";
        String obj3 = "MULTI{key1=value1;key2=value2,key3=value3},";
        String logline = "2013-06-25 15:02:08,876 MultiThreadContext[" + obj1 + obj2 + obj3 + "]";
        logger.info(logline);
        Map result = parser.process(logline);

        String[][] validate = new String[][]{
            {"timestamp", "2013-06-25 15:02:08,876"},
            {"MultiThreadContext.Simple.key1", "value1"},
            {"MultiThreadContext.Hard.componded.name.on:key", "1231A..SD"},
            {"MultiThreadContext.MULTI.key1", "value1"},
            {"MultiThreadContext.MULTI.key2", "value2"},
            {"MultiThreadContext.MULTI.key3", "value3"}
        };
        for (String[] entry : validate) {
            assertEquals("Requested " + entry[0], entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }

    @Test
    public void malformedStringTest() {
        logger.info("emptyStringTest");
        String logline = "abbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabba";
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail("empty-string should not cause an exception");
        }
    }
    @Test
    public void emptyStringTest() {
        logger.info("emptyStringTest");
        String logline = "";
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail("empty-string should not cause an exception");
        }
    }
    @Test
    public void nullStringTest() {
        logger.info("nullStringTest");
        String logline = null;
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail("null-string should not cause an exception");
        }
    }
}