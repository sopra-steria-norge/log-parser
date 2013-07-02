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
        map.put("key:duration", "value");
        map.put("key.something.something.duration", "val");
        map.put("", "value");
        map.put(null, "value");
        map.put("", null);
        map.put(null, null);
        boolean[] expResult = {false, true, true, true, false, false, false, false};

        Set<Map.Entry<String, String>> entries = map.entrySet();
        int i = 0;
        for (Entry<String, String> entry : entries) {
            assertEquals(expResult[i++], GuavaHelpers.isDuration().apply(entry));
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
        map.put("LocalThreadContext.id", "myID");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);
        TimeMeasurement result = null;
        TimeMeasurement expResult = TimeMeasurement.create("myID", "PT0.015S");
        for (Entry<String, String> e : map.entrySet()) {
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
        map.put("LocalThreadContext.id", "myID");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);
        TimeMeasurement result = null;
        TimeMeasurement expResult = TimeMeasurement.create("myID", "PT0.015S");
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
        map.put("LocalThreadContext.id", "myID");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);
        TimeMeasurement result = null;
        TimeMeasurement expResult = TimeMeasurement.create("myID", "PT0.015S");
        for (Entry<String, String> e : map.entrySet()) {
            if (e.getKey().endsWith("duration")) {
                result = functor.apply(e);
            }
        }
    }

    @Test
    public void testGetConverterMultiOK() {
        System.out.println("testGetConverterMultiOK");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("DoesntNeedIt.Total.duration", "PT0.015S");
        map.put("DoesntNeedIt.Wait.duration", "PT47.061S");
        map.put("DoesntNeedIt.Lap.Class.method:duration", "PT0.015S");
        map.put("DoesntNeedIt.Lap.Class.function:duration", "PT0.005S");
        Function<Map.Entry<String, String>, TimeMeasurement> functor = GuavaHelpers.getConverter(map);

        TimeMeasurement[] expResult = new TimeMeasurement[]{
            TimeMeasurement.create("Total", "PT0.015S"),
            TimeMeasurement.create("Wait", "PT47.061S"),
            TimeMeasurement.create("Class.method", "PT0.015S"),
            TimeMeasurement.create("Class.function", "PT0.005S")
        };
        int expResultCounter = 0;
        for (Entry<String, String> e : map.entrySet()) {
            if (e.getKey().endsWith("duration")) {
                assertEquals(expResult[expResultCounter++], functor.apply(e));
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
}