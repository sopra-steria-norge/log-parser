/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.route;

import no.osl.cdms.profile.route.ParserImpl;
import java.util.Map;
import no.osl.cdms.profile.interfaces.Parser;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author nutgaard
 */
public class MockParserTest {
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
        System.out.println("localMockLogLine");
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
        System.out.println("multiMockLogLine");
        String obj1 = "Simple{key1=value1},";
        String obj2 = "Hard{componded.name.on:key=1231A..SD},";
        String obj3 = "MULTI{key1=value1;key2=value2,key3=value3},";
        String logline = "2013-06-25 15:02:08,876 MultiThreadContext[" + obj1 + obj2 + obj3 + "]";
        System.out.println(logline);
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
        System.out.println("emptyStringTest");
        String logline = "abbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabbaabba";
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("empty-string should not cause an exception");
        }
    }
    @Test
    public void emptyStringTest() {
        System.out.println("emptyStringTest");
        String logline = "";
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("empty-string should not cause an exception");
        }
    }
    @Test
    public void nullStringTest() {
        System.out.println("nullStringTest");
        String logline = null;
        try {
            Map result = parser.process(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("null-string should not cause an exception");
        }
    }
}