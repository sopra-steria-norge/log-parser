/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.models.TimeMeasurement;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class AnalyzerTest {

    public final double[] data = {43, 54, 56, 61, 62, 66, 68, 69, 69, 70, 71, 72, 77, 78, 79, 85, 87, 88, 89, 93, 95, 96, 98, 99, 99};
    private List<TimeMeasurement> tms;
    private DataAnalyzer analyzer;

    public AnalyzerTest() {
    }

    @Before
    public void setUp() {
        tms = new LinkedList<TimeMeasurement>();
        for (double d : data) {
            tms.add(new TimeMeasurement("WAIT0", d));
        }
        this.analyzer =new Analyzer(tms);
    }

    @After
    public void tearDown() {
        tms.clear();
        tms = null;
    }

    @Test
    public void testSorted() {
        System.out.println("sorted");
        String id = "total";
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
        String id = "total";
        double expResult = 76.96;
        double result = analyzer.average(id);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of stddev method, of class Analyzer.
     */
    @Test
    public void testStddev() {
        System.out.println("stddev");
        String id = "total";
        double expResult = 15.27214;
        double result = analyzer.stddev(id);
        assertEquals(expResult, result, 0.00001);
    }

    /**
     * Test of percentile method, of class Analyzer.
     */
    @Test
    public void testPercentile() {
        System.out.println("percentile 20");
        String id = "total";
        int k = 20;
        double expResult = 64.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testPercentileIndex() {
        System.out.println("percentile 90");
        String id = "total";
        int k = 90;
        double expResult = 98.0;
        double result = analyzer.percentile(id, k);
        assertEquals(expResult, result, 0.0);

    }

    /**
     * Test of buckets method, of class Analyzer.
     */
    @Test
    public void testBuckets() {
        System.out.println("buckets");
        String id = "total";
        int NOFBuckets = 5;
        double[] expResult = {3, 7, 5, 8, 2};
        double[] result = analyzer.buckets(id, NOFBuckets);
        System.out.println(Arrays.toString(result));
        for (int ind = 0; ind < expResult.length; ind++) {
            assertEquals(expResult[ind], result[ind], 0.00001);
        }
    }
}