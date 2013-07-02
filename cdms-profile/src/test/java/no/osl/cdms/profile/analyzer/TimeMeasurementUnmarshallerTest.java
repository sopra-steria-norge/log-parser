/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.models.TimeMeasurement;
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
public class TimeMeasurementUnmarshallerTest {
    
    public TimeMeasurementUnmarshallerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of unmarshall method, of class TimeMeasurementUnmarshaller.
     */
    @Test
    public void testUnmarshall_List() {
        System.out.println("unmarshall");
        //Build testing data
        List<Map<String, String>> data = Lists.newLinkedList();
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
        
        TimeMeasurementUnmarshaller instance = new TimeMeasurementUnmarshaller();
        TimeMeasurement[] expResult = new TimeMeasurement[]{
           TimeMeasurement.create("myID", "PT1.15S"),
           TimeMeasurement.create("Total","PT1.15S"),
           TimeMeasurement.create("Wait","PT1.15S"),
           TimeMeasurement.create("Class.method","PT2.151S")
        };
        
        List<TimeMeasurement> result = instance.unmarshall(data);
        for (int i = 0; i < expResult.length; i++) {
            assertEquals(expResult[i], result.get(i));
        }
    }
}