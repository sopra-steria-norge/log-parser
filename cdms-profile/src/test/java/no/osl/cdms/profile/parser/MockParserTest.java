/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class MockParserTest {

    public MockParserTest() {
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void localMockLogLine() {
        System.out.println("localMockLogLine");
        String logline = "2013-06-25 15:02:08,876 ObjName[key1=value1,key2=ObjName2[key1=value1,key2=value2,key3=ObjName3[key1=value1]]]";
        Map<String, String> result = new LogLineRegexParser().parse(logline);

        String[][] validate = new String[][]{
            {"timestamp", "2013-06-25 15:02:08,876"},
            {"ObjName.key1", "value1"},
            {"ObjName.key2", "ObjName2"},
            {"ObjName.key2.key1", "value1"},
            {"ObjName.key2.key2", "value2"},
            {"ObjName.key2.key3", "ObjName3"},
            {"ObjName.key2.key3.key1", "value1"}
                
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
        String obj4 = "NESTED{Inner[key1=value1,key2=value2]}";
        String logline = "2013-06-25 15:02:08,876 MultiObj[" + obj1 + obj2 + obj3 + obj4 + "]";
        System.out.println(logline);
        Map result = new LogLineRegexParser().parse(logline);

        String[][] validate = new String[][]{
            {"timestamp", "2013-06-25 15:02:08,876"},
            {"MultiObj.Simple.key1", "value1"},
            {"MultiObj.Hard.componded.name.on:key", "1231A..SD"},
            {"MultiObj.MULTI.key1", "value1"},
            {"MultiObj.MULTI.key2", "value2"},
            {"MultiObj.MULTI.key3", "value3"},
            {"MultiObj.NESTED.Inner.key1", "value1"},
            {"MultiObj.NESTED.Inner.key2", "value2"}
        };
        for (String[] entry : validate) {
            assertEquals("Requested " + entry[0], entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }

    @Test
    public void mapReuse() {
        System.out.println("mapReuse");
        String obj1 = "Simple{key1=value1},";
        String obj2 = "Hard{componded.name.on:key=1231A..SD},";
        String obj3 = "MULTI{key1=value1;key2=value2,key3=value3},";
        String obj4 = "NESTED{Inner[key1=value1,key2=value2]}";
        String logline = "2013-06-25 15:02:08,876 MultiObj[" + obj1 + obj2 + obj3 + obj4 + "]";
        Map result = new LogLineRegexParser().parse(logline);
        String logline2 = "2013-06-25 15:02:08,876 ObjName[key1=value1,key2=ObjName2[key1=value1,key2=value2,key3=ObjName3[key1=value1]]]";
        result = new LogLineRegexParser().parse(result, logline2);

        String[][] validate = new String[][]{
            {"timestamp", "2013-06-25 15:02:08,876"},
            {"ObjName.key1", "value1"},
            {"ObjName.key2", "ObjName2"},
            {"ObjName.key2.key1", "value1"},
            {"ObjName.key2.key2", "value2"},
            {"ObjName.key2.key3", "ObjName3"},
            {"ObjName.key2.key3.key1", "value1"},
            {"MultiObj.Simple.key1", "value1"},
            {"MultiObj.Hard.componded.name.on:key", "1231A..SD"},
            {"MultiObj.MULTI.key1", "value1"},
            {"MultiObj.MULTI.key2", "value2"},
            {"MultiObj.MULTI.key3", "value3"},
            {"MultiObj.NESTED.Inner.key1", "value1"},
            {"MultiObj.NESTED.Inner.key2", "value2"}
        };
        for (String[] entry : validate) {
            assertEquals("Requested " + entry[0], entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }

    @Test
    public void emptyStringTest() {
        System.out.println("emptyStringTest");
        String logline = "";
        try {
            Map result = new LogLineRegexParser().parse(logline);
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
            Map result = new LogLineRegexParser().parse(logline);
            assertEquals(0, result.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail("null-string should not cause an exception");
        }
    }
    //@Test
    public void nullMapTest() {
        System.out.println("nullmapTest");
        Map m = null;
        String logline = "Test[key=val]";
        try {
            Map r = new LogLineRegexParser().parse(m, logline);
        }catch (Exception e) {
            e.printStackTrace();
            fail("null-map should not cause an exception");
        }
    }
}