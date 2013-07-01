/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class MultiThreadParserTest {
    
    public MultiThreadParserTest() {
    }
    
    
    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String obj1 = "Simple{key1=value1},";
        String obj2 = "Hard{componded.name.on:key=1231A..SD},";
        String obj3 = "MULTI{key1=value1;key2=value2,key3=value3},";
        String obj4 = "NESTED{Inner[key1=value1,key2=value2]}";
        String logline = "MultiObj["+obj1+obj2+obj3+obj4+"]";
        System.out.println(logline);
        LogLineRegexParser instance = new LogLineRegexParser();
        Map result = instance.parse(logline);
        
        String[][] validate = new String[][]{
            {"MultiObj.Simple.key1","value1"},
            {"MultiObj.Hard.componded.name.on:key","1231A..SD"},
            {"MultiObj.MULTI.key1","value1"},
            {"MultiObj.MULTI.key2","value2"},
            {"MultiObj.MULTI.key3","value3"},
            {"MultiObj.NESTED.Inner.key1","value1"},
            {"MultiObj.NESTED.Inner.key2","value2"}
        };
        for (String[] entry : validate){
            assertEquals("Requested "+entry[0],entry[1], result.get(entry[0]));
        }
        assertEquals(validate.length, result.size());
    }
}