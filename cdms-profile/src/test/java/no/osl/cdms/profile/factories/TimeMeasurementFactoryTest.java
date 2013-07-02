/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import no.osl.cdms.profile.interfaces.TimeMeasurement;
import no.osl.cdms.profile.models.TimeMeasurementImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurementFactoryTest {
    /**
     * Test of create method, of class TimeMeasurementFactory.
     */
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
}