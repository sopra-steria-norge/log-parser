/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.utilities;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import no.osl.cdms.profile.api.Measured;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.factories.EntityFactory;
import no.osl.cdms.profile.log.MeasuredEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
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
public class GuavaHelpersTest {

    public GuavaHelpersTest() {
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
     * Test of isDuration method, of class GuavaHelpers.
     */
    @Test
    public void testIsDuration() {
        System.out.println("isDuration");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("key", "value");
        map.put("key.duration", "value");
        map.put("key.something.something.duration", "val");
        map.put("", "value");
        map.put("keys.duration", "null");
        map.put(null, "value");
        map.put("", null);
        map.put(null, null);
        boolean[] expResult = {false, true, true, false, false, false, false, false};
        Set<Map.Entry<String, String>> entries = map.entrySet();
        int i = 0;
        for (Entry<String, String> entry : entries) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
            assertEquals("Testing " + i, expResult[i++], GuavaHelpers.isDuration().apply(entry));
        }
    }

    /**
     * Test of getConverter method, of class GuavaHelpers.
     */
    @Test
    public void testGetConverterLocalOK() {
        System.out.println("getConverterLocalOK");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("LocalThreadContext.duration", "PT0.015S");
        map.put("LocalThreadContext.id", "myID.test");
        map.put("timestamp", "2013-06-25 15:02:08,876");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        TimeMeasurement result = null;
        TimeMeasurement expResult = EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.015S");
        Measured expSub = EntityFactory.createMeasured("myID", "test");
        expSub.getTimeMeasurements().add((TimeMeasurementEntity) expResult);
        expResult.setMeasured((MeasuredEntity) expSub);

        for (Entry<String, String> e : map.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
            if (e.getKey().endsWith("duration")) {
                result = functor.apply(e);
            }
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testGetConverterLocalWierdTimeFormat() {
        System.out.println("testGetConverterLocalWierdTimeFormat");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("LocalThreadContext.duration", "15.0");
        map.put("LocalThreadContext.id", "myID.test");
        map.put("timestamp", "2013-06-25 15:02:08,876");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        TimeMeasurement result = null;
        TimeMeasurement expResult = EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.015S");
        Measured expSub = EntityFactory.createMeasured("myID", "test");
        expSub.getTimeMeasurements().add((TimeMeasurementEntity) expResult);
        expResult.setMeasured((MeasuredEntity) expSub);

        for (Entry<String, String> e : map.entrySet()) {
            if (e.getKey().endsWith("duration")) {
                result = functor.apply(e);
            }
        }
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConverterLocalWrongFormat() {
        System.out.println("testGetConverterLocalWrongFormat");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("LocalThreadContext.duration", "15.0aasda");
        map.put("LocalThreadContext.id", "myID.test");
        map.put("timestamp", "2013-06-25 15:02:08,876");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        TimeMeasurement result = null;
        TimeMeasurement expResult = EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.015S");
        Measured expSub = EntityFactory.createMeasured("myID", "test");
        expSub.getTimeMeasurements().add((TimeMeasurementEntity) expResult);
        expResult.setMeasured((MeasuredEntity) expSub);

        for (Entry<String, String> e : map.entrySet()) {
            if (e.getKey().endsWith("duration")) {
                result = functor.apply(e);
            }
        }
        System.out.println("result: " + result);
    }

    @Test
    public void testGetConverterMultiOK() {
        System.out.println("testGetConverterMultiOK");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("DoesntNeedIt.Total.duration", "PT0.015S");
        map.put("DoesntNeedIt.Wait.duration", "PT47.061S");
        map.put("DoesntNeedIt.Lap.Class.method:duration", "PT0.015S");
        map.put("DoesntNeedIt.Lap.Class.function:duration", "PT0.005S");
        map.put("timestamp", "2013-06-25 15:02:08,876");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        TimeMeasurement[] expResult = new TimeMeasurement[]{
            EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.015S"),
            EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT47.061S"),
            EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.015S"),
            EntityFactory.createTimeMeasurement("2013-06-25 15:02:08,876", "PT0.005S")
        };
        Measured[] expSub = new Measured[]{
            EntityFactory.createMeasured("Total", ""),
            EntityFactory.createMeasured("Wait", ""),
            EntityFactory.createMeasured("Class", "method"),
            EntityFactory.createMeasured("Class", "function")
        };
        for (int i = 0; i < expResult.length; i++) {
            expSub[i].getTimeMeasurements().add((TimeMeasurementEntity) expResult[i]);
            expResult[i].setMeasured((MeasuredEntity) expSub[i]);
        }
        int expResultCounter = 0;
        for (Entry<String, String> e : map.entrySet()) {
            if (e.getKey().endsWith("duration")) {
                TimeMeasurement tm = functor.apply(e);
                if (!tm.equals(expResult[expResultCounter])) {
                    System.out.println(expResult[expResultCounter]);
                    System.out.println(tm);
                }
                assertEquals(expResult[expResultCounter++], tm);
            }
        }
    }

    @Test
    public void testGetConverterMultiIllegalArguments() {
        System.out.println("testGetConverterMultiIllegalArguments");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("DoesntNeedIt.Wait.duration", null);
        map.put("DoesntNeedIt.Lap.Class.method:duration", "");
        map.put("", "PT0.005S");
        map.put("JustTesting", "PT0.005S");
        map.put(null, "PT0.015S");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        Class[] expExceptions = new Class[]{
            IllegalArgumentException.class,
            IllegalArgumentException.class,
            NullPointerException.class,
            NullPointerException.class,
            IllegalArgumentException.class
        };
        int expResultCounter = 0;
        for (Entry<String, String> e : map.entrySet()) {
            try {
                functor.apply(e);
            } catch (Exception ex) {
                if (!ex.getClass().equals(expExceptions[expResultCounter])) {
                    System.out.println("Key: " + e.getKey() + " Input: " + e.getValue());
                    ex.printStackTrace();
                }
                assertEquals("Tested entry " + expResultCounter, expExceptions[expResultCounter], ex.getClass());
            } finally {
                expResultCounter++;
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void testGetConverterLocalMissingId() {
        System.out.println("testGetConverterLocalMissingId");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("LocalThreadContext.duration", "15.0");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);
        for (Entry<String, String> e : map.entrySet()) {
            functor.apply(e);
        }
    }
}