/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.interfaces.TimeMeasurement;
import no.osl.cdms.profile.analyzer.TimeMeasurementImpl;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurementFactoryTest {
    List<Map<String, String>> data;
    TimeMeasurement[] listExp;
    /**
     * Test of create method, of class TimeMeasurementFactory.
     */
    @Before
    public void setUp() {
        data = Lists.newLinkedList();
        Map<String, String> map1 = Maps.newLinkedHashMap();
        map1.put("LocalThreadContext.id", "myID");
        map1.put("LocalThreadContext.duration", "PT1.15S");
        map1.put("LocalThreadContext.random", "null");
        data.add(map1);
        Map<String, String> map2 = Maps.newLinkedHashMap();
        map2.put("Something.Total.duration", "PT1.15S");
        map2.put("Something.Wait.duration", "PT1.15S");
        map2.put("Something.Lap.Class.method:duration", "PT2.151S");
        data.add(map2);
        
        listExp = new TimeMeasurement[]{
           TimeMeasurementFactory.create("myID", "PT1.15S"),
           TimeMeasurementFactory.create("Total","PT1.15S"),
           TimeMeasurementFactory.create("Wait","PT1.15S"),
           TimeMeasurementFactory.create("Class.method","PT2.151S")
        };
    }
    
    @After
    public void tearDown() {
        data.clear();
        data = null;
        listExp = null;
    }
    
    @Test
    public void testCreate_String_String_joda() {
        System.out.println("create");
        String name = "test";
        String time = "PT1.123S";
        TimeMeasurement expResult = new TimeMeasurementImpl(name, 1123);
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(expResult, result);
    }
    @Test
    public void testCreate_String_String_Double() {
        System.out.println("create");
        String name = "test";
        String time = "1123";
        TimeMeasurement expResult = new TimeMeasurementImpl(name, 1123);
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(expResult, result);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_String_String_Error() {
        System.out.println("create");
        String name = "test";
        String time = "1123asda";
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
    }

    /**
     * Test of create method, of class TimeMeasurementFactory.
     */
    @Test
    public void testCreate_String_double() {
        System.out.println("create");
        String name = "test";
        double time = 0.0;
        TimeMeasurement expResult = new TimeMeasurementImpl(name, time);
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(expResult, result);
    }
    @Test
    public void testUnmarshall_List() {
        System.out.println("create::list");
        //Build testing data
        
               
        List<TimeMeasurement> result = TimeMeasurementFactory.create(data);
        for (int i = 0; i <  listExp.length; i++) {
            assertEquals(listExp[i], result.get(i));
        }
    }
    @Test
    public void testUnmarshall_List_null(){
        System.out.println("testUnmarshall_List_null");        
        List<TimeMeasurement> result = TimeMeasurementFactory.create(null, data);
        for (int i = 0; i < listExp.length; i++) {
            assertEquals(listExp[i], result.get(i));
        }
    }
    @Test
    public void testUnmarshall_Data_null() {
        System.out.println("testUnmarshall_Data_null");        
        List<TimeMeasurement> result = TimeMeasurementFactory.create((List<Map<String, String>>)null);
        assertEquals(0, result.size());
    }
}