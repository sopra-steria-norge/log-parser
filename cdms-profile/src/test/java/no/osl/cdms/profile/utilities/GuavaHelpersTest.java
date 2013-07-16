/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.utilities;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.factories.EntityFactory;
import no.osl.cdms.profile.log.LogRepository;
import org.joda.time.DateTime;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.InstantConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
public class GuavaHelpersTest {

    public GuavaHelpersTest() {
    }

    @Autowired
    private LogRepository logRepository;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        EntityFactory.getInstance().setLogRepository(logRepository);
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
        Procedure expSub = EntityFactory.getInstance().createProcedure("myID", "test");
        TimeMeasurement expResult = EntityFactory.getInstance().createTimeMeasurement(expSub,
                GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.015S");

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
        Procedure expSub = EntityFactory.getInstance().createProcedure("myID", "test");

        TimeMeasurement expResult = EntityFactory.getInstance().createTimeMeasurement(expSub,
                GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.015S");

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
        Procedure expSub = EntityFactory.getInstance().createProcedure("myID", "test");
        TimeMeasurement expResult = EntityFactory.getInstance().createTimeMeasurement(expSub,
                GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.015S");

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

        Procedure[] expSub = new Procedure[]{
                EntityFactory.getInstance().createProcedure("Total", ""),
                EntityFactory.getInstance().createProcedure("Wait", ""),
                EntityFactory.getInstance().createProcedure("Class", "method"),
                EntityFactory.getInstance().createProcedure("Class", "function")
        };
        TimeMeasurement[] expResult = new TimeMeasurement[]{
            EntityFactory.getInstance().createTimeMeasurement(expSub[0],
                    GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.015S"),
            EntityFactory.getInstance().createTimeMeasurement(expSub[1],
                    GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT47.061S"),
            EntityFactory.getInstance().createTimeMeasurement(expSub[2],
                    GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.015S"),
            EntityFactory.getInstance().createTimeMeasurement(expSub[3],
                    GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.005S")
        };
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