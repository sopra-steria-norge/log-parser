/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.interfaces.DataAnalyzer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.factories.EntityFactory;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class AnalyzerTest {

    public final int[] data = {43, 54, 56, 61, 62, 66, 68, 69, 69, 70, 71, 72, 77, 78, 79, 85, 87, 88, 89, 93, 95, 96, 98, 99, 99};
    private List<TimeMeasurement> tms;
    private DataAnalyzer analyzer;
    private String id = "WAIT0";

    @Autowired
    private LogRepository logRepository;

    public AnalyzerTest() {
    }

    @Before
    public void setUp() {
        EntityFactory.getInstance().setLogRepository(logRepository);
        tms = new LinkedList<TimeMeasurement>();
        ProcedureEntity me = (ProcedureEntity)EntityFactory.getInstance().createProcedure(id, "");
        ProcedureEntity me2 = (ProcedureEntity)EntityFactory.getInstance().createProcedure("WAIT1", "");
        for (double d : data) {
            TimeMeasurement tm = EntityFactory.getInstance().createTimeMeasurement(me,
                    GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT"+String.valueOf(d/1000)+"S");
            tms.add(tm);
        }
        TimeMeasurement tm2 = EntityFactory.getInstance().createTimeMeasurement(me2,
                GuavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.0"+String.valueOf(9999/1000)+"S");
        tms.add(tm2);//Indirect test of delegate
        this.analyzer = new Analyzer(tms);
    }

    @After
    public void tearDown() {
        tms.clear();
        tms = null;
    }

    @Test
    public void setupNull() {
        System.out.println("AnalyzerSetup::null");
        System.out.println("Sending null");
        Analyzer a = new Analyzer(null);
        assertEquals(0, a.average("total"), 0);
        assertEquals(0, a.stddev("total"), 0);
        assertEquals(0, a.percentile("total", 50), 0);
        int[] buckets = a.buckets("total", 10);
        for (int i : buckets){
            assertEquals(0, i, 0);
        }
    }

    @Test
    public void testSorted() {
        System.out.println("sorted");
        double expResult = 76.96;
        double result = analyzer.average(id);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of average method, of class Analyzer.
     */
    @Test
    public void testAverage() {
        System.out.println("average");
        double expResult = 76.96;
        double result = analyzer.average(id);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testAverage_null() {
        System.out.println("average_null");
        String id = "not";
        double expResult = 0;
        assertEquals(expResult, analyzer.average(id), 0.0);
    }

    /**
     * Test of stddev method, of class Analyzer.
     */
    @Test
    public void testStddev() {
        System.out.println("stddev");
        double expResult = 15.27214;
        double result = analyzer.stddev(id);
        assertEquals(expResult, result, 0.00001);
    }

    @Test
    public void testStddev_null() {
        System.out.println("stddev_null");
        String id = "not";
        double expResult = 0;
        double result = analyzer.stddev(id);
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of percentile method, of class Analyzer.
     */
    @Test
    public void testPercentile() {
        System.out.println("percentile 20");
        int k = 20;
        double expResult = 64.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
     public void testPercentile2() {
        System.out.println("percentile 50");
        int k = 50;
        double expResult = 77.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile3() {
        System.out.println("percentile 0");
        int k = 0;
        double expResult = 43.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile4() {
        System.out.println("percentile 100");
        int k = 100;
        double expResult = 99.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile_null() {
        System.out.println("percentile 20_null");
        String id = "not";
        int k = 20;
        double expResult = 0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentileIndex() {
        System.out.println("percentile 90");
        int k = 90;
        double expResult = 98.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testPercentileIndex_null() {
        System.out.println("percentile 90_null");
        String id = "not";
        int k = 90;
        double expResult = 0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of buckets method, of class Analyzer.
     */
    @Test
    public void testBuckets() {
        System.out.println("buckets");
        int NOFBuckets = 5;
        int[] expResult = {3, 7, 5, 8, 2};
        int[] result = analyzer.buckets(id, NOFBuckets);
        System.out.println(Arrays.toString(result));
        for (int ind = 0; ind < expResult.length; ind++) {
            assertEquals(expResult[ind], result[ind], 0.00001);
        }
    }
    @Test
    public void testBuckets_null() {
        System.out.println("buckets_null");
        String id = "not";
        int NOFBuckets = 5;
        int[] expResult = {0, 0, 0, 0, 0};
        int[] result = analyzer.buckets(id, NOFBuckets);
        System.out.println(Arrays.toString(result));
        for (int ind = 0; ind < expResult.length; ind++) {
            assertEquals(expResult[ind], result[ind], 0.00001);
        }
    }
}