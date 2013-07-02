/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.models;

import no.osl.cdms.profile.factories.TimeMeasurementFactory;
import no.osl.cdms.profile.interfaces.TimeMeasurement;
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
        TimeMeasurement expResult = new TimeMeasurementImpl(name, 2123);
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(expResult, result);
    }
    @Test
    public void testCreate_time_joda_duration() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "PT2.123S";
        double exp = 2123;
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(exp, result.getTime(), 0);
    }
    @Test
    public void testCreate_time_parse_double_fallback() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "2123";
        double exp = 2123;
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
        assertEquals(exp, result.getTime(), 0);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreate_time_error() {
        System.out.println("TimeMeasurement::create");
        String name = "test";
        String time = "2iasd1231asd";
        TimeMeasurement result = TimeMeasurementFactory.create(name, time);
    }
    /**
     * Test of compareTo method, of class TimeMeasurement.
     */
    @Test
    public void testCompareTo() {
        System.out.println("TimeMeasurement::compareTo");
        TimeMeasurement o = TimeMeasurementFactory.create("Test", "1123");
        TimeMeasurement o2 = TimeMeasurementFactory.create("Test", "PT1.123S");
        TimeMeasurement o3 = new TimeMeasurementImpl("Test", 1123);
        TimeMeasurement small = new TimeMeasurementImpl("Test", 11);
        TimeMeasurement big = new TimeMeasurementImpl("Test", 9999);
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
        TimeMeasurement instance = TimeMeasurementFactory.create("test", "PT0.123S");
        TimeMeasurement instance2 = TimeMeasurementFactory.create(null, "PT0.123S");
        TimeMeasurement ok = new TimeMeasurementImpl("test", 123);
        TimeMeasurement ok2 = TimeMeasurementFactory.create("test", "123");
        TimeMeasurement wrongname = TimeMeasurementFactory.create("test2", "PT0.123S");
        TimeMeasurement wrongtime = TimeMeasurementFactory.create("test", "PT0.124S");
        TimeMeasurement nullname = new TimeMeasurementImpl(null, 123);
        TimeMeasurement big = new TimeMeasurementImpl(null, 1234564464);
        
        
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