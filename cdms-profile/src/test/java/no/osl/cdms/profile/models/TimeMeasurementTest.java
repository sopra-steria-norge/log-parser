/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.models;

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
public class TimeMeasurementTest {
     /**
     * Test of create method, of class TimeMeasurement.
     */
    @Test
    public void testCreate_ok() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "PT2.123S";
        TimeMeasurement expResult = new TimeMeasurement(name, 2123);
        TimeMeasurement result = TimeMeasurement.create(name, time);
        assertEquals(expResult, result);
    }
    @Test
    public void testCreate_time_joda_duration() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "PT2.123S";
        double exp = 2123;
        TimeMeasurement result = TimeMeasurement.create(name, time);
        assertEquals(exp, result.time, 0);
    }
    @Test
    public void testCreate_time_parse_double_fallback() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "2123";
        double exp = 2123;
        TimeMeasurement result = TimeMeasurement.create(name, time);
        assertEquals(exp, result.time, 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_time_error() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "2iasd1231asd";
        TimeMeasurement result = TimeMeasurement.create(name, time);
    }
    /**
     * Test of compareTo method, of class TimeMeasurement.
     */
    @Test
    public void testCompareTo() {
        System.out.println("TimeMeasurement::compareTo");
        TimeMeasurement o = TimeMeasurement.create("Test", "1123");
        TimeMeasurement o2 = TimeMeasurement.create("Test", "PT1.123S");
        TimeMeasurement o3 = new TimeMeasurement("Test", 1123);
        TimeMeasurement small = new TimeMeasurement("Test", 11);
        TimeMeasurement big = new TimeMeasurement("Test", 9999);
        int expResult = 0;
        int result = o.compareTo(o2);
        assertEquals(expResult, result);
        result = o.compareTo(o3);
        assertEquals(expResult, result);
        result = o2.compareTo(o3);
        assertEquals(expResult, result);
        
        result = small.compareTo(big);
        assertEquals(-1, result);
        
        result = big.compareTo(small);
        assertEquals(1, result);
    }

    /**
     * Test of equals method, of class TimeMeasurement.
     */
    @Test
    public void testEquals() {
        System.out.println("TimeMeasurement::equals");
        TimeMeasurement instance = TimeMeasurement.create("test", "PT0.123S");
        TimeMeasurement instance2 = TimeMeasurement.create(null, "PT0.123S");
        TimeMeasurement ok = new TimeMeasurement("test", 123);
        TimeMeasurement ok2 = TimeMeasurement.create("test", "123");
        TimeMeasurement wrongname = TimeMeasurement.create("test2", "PT0.123S");
        TimeMeasurement wrongtime = TimeMeasurement.create("test", "PT0.124S");
        TimeMeasurement nullname = new TimeMeasurement(null, 123);
        TimeMeasurement big = new TimeMeasurement(null, 1234564464);
        
        
        assertTrue(instance.equals(instance));
        assertTrue(instance.equals(ok));
        assertTrue(instance.equals(ok2));
        assertTrue(instance2.equals(instance2));
        assertTrue(ok.equals(ok));
        assertTrue(ok2.equals(ok2));
        assertTrue(wrongname.equals(wrongname));
        assertTrue(wrongtime.equals(wrongtime));
        assertTrue(nullname.equals(nullname));
        assertFalse(big.equals(nullname));
        assertFalse(instance.equals(wrongname));
        assertFalse(instance.equals(wrongtime));       
        assertFalse(instance.equals(null));       
        assertFalse(instance.equals("hei"));
        assertFalse(instance.equals(nullname));
        assertFalse(instance2.equals(instance));
        
    }
}