/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class LocalThreadParserTest {

    public LocalThreadParserTest() {
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String logline = "ObjName[key1=value1,key2=ObjName2[key1=value1,key2=value2,key3=ObjName3[key1=value1]]]";
        LogLineRegexParser instance = new LogLineRegexParser();
        Map result = instance.parse(logline);
        
        String[][] validate = new String[][]{
            {"ObjName.key1","value1"},
            {"ObjName.key2","ObjName2"},
            {"ObjName.key2.key1","value1"},
            {"ObjName.key2.key2","value2"},
            {"ObjName.key2.key3","ObjName3"},
            {"ObjName.key2.key3.key1","value1"}
        };
        for (String[] entry : validate){
            assertEquals(entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }
}