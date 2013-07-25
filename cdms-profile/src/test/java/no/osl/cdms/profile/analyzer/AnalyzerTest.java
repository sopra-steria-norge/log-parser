package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.interfaces.DataAnalyzer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import no.osl.cdms.profile.interfaces.EntityFactory;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.joda.time.DateTime;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    private DataAnalyzer analyzer;
    private int id1 = 1;
    private int id2 = 2;
    private int id3 = 3;
    private List<TimeMeasurement> timeMeasurements1;
    private List<TimeMeasurement> timeMeasurements2;
    private List<TimeMeasurement> timeMeasurements3;

    private LogRepository logRepository;

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private GuavaHelpers guavaHelpers;

    private static DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");

    public AnalyzerTest() {
    }

    @Before
    public void setUp() {
        logRepository = mock(LogRepository.class);

        ProcedureEntity p1 = new ProcedureEntity("Name1", "Class name1", "Method name 1");
        ProcedureEntity p2 = new ProcedureEntity("Name2", "Class name2", "Method name 2");
        ProcedureEntity p3 = new ProcedureEntity("Name3", "Class name3", "Method name 3");
        p1.setId(id1);
        p2.setId(id2);
        p3.setId(id3);
        when(logRepository.getProcedure(id1)).thenReturn(p1);
        when(logRepository.getProcedure(id2)).thenReturn(p2);
        when(logRepository.getProcedure(id3)).thenReturn(p3);

        entityFactory.setLogRepository(logRepository);

        timeMeasurements1 = new LinkedList<TimeMeasurement>();
        timeMeasurements2 = new LinkedList<TimeMeasurement>();
        timeMeasurements3 = new LinkedList<TimeMeasurement>();

        ProcedureEntity procedure1 = logRepository.getProcedure(id1);
        ProcedureEntity procedure2 = logRepository.getProcedure(id2);
        ProcedureEntity procedure3 = logRepository.getProcedure(id3);

        for (double d : data) {
            TimeMeasurement tm = entityFactory.createTimeMeasurement(procedure1,
                    guavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT" + String.valueOf(d / 1000) + "S");
            timeMeasurements1.add(tm);
        }
        TimeMeasurement tm2 = entityFactory.createTimeMeasurement(procedure2,
                guavaHelpers.parseDateString("2013-06-25 15:02:08,876"), "PT0.0" + String.valueOf(9999.0 / 1000) + "S");
        timeMeasurements2.add(tm2);//Indirect test of delegate

        // Bucket measurements
        timeMeasurements3.add(entityFactory.createTimeMeasurement(procedure3,
                guavaHelpers.parseDateString("2013-06-01 15:02:08,876"), "PT" + String.valueOf(50.0 / 1000) + "S"));
        timeMeasurements3.add(entityFactory.createTimeMeasurement(procedure3,
                guavaHelpers.parseDateString("2013-06-02 15:02:08,876"), "PT" + String.valueOf(154.0 / 1000) + "S"));
        timeMeasurements3.add(entityFactory.createTimeMeasurement(procedure3,
                guavaHelpers.parseDateString("2013-06-02 16:05:08,876"), "PT" + String.valueOf(45.0 / 1000) + "S"));
        timeMeasurements3.add(entityFactory.createTimeMeasurement(procedure3,
                guavaHelpers.parseDateString("2014-06-02 16:05:08,876"), "PT" + String.valueOf(455.0 / 1000) + "S"));
        timeMeasurements3.add(entityFactory.createTimeMeasurement(procedure3,
                guavaHelpers.parseDateString("2014-06-02 16:05:10,876"), "PT" + String.valueOf(123.0 / 1000) + "S"));
        this.analyzer = new Analyzer();
    }

    @After
    public void tearDown() {
        timeMeasurements1.clear();
        timeMeasurements2.clear();
        timeMeasurements3.clear();
        timeMeasurements1 = null;
        timeMeasurements2 = null;
        timeMeasurements3 = null;
    }

    @Test
    public void setupNull() {
        System.out.println("AnalyzerSetup::null");
        System.out.println("Sending null");
        Analyzer a = new Analyzer();
        assertEquals(0, a.average(null), 0);
        //assertEquals(0, a.stddev(id), 0);
        assertEquals(0, a.percentile(null, 50), 0);
        int[] buckets = a.buckets(null, 10);
        for (int i : buckets){
            assertEquals(0, i, 0);
        }
    }

    @Test
    public void testSorted() {
        System.out.println("sorted");
        double expResult = 76.96;
        double result = analyzer.average(timeMeasurements1);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of average method, of class Analyzer.
     */
    @Test
    public void testAverage() {
        System.out.println("average");
        double expResult = 76.96;
        double result = analyzer.average(timeMeasurements1);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testAverage_null() {
        System.out.println("average_null");
        double expResult = 0;
        assertEquals(expResult, analyzer.average(null), 0.0);
    }

    /**
     * Test of stddev method, of class Analyzer.
     */
//    @Test
//    public void testStddev() {
//        System.out.println("stddev");
//        double expResult = 15.27214;
//        double result = analyzer.stddev(id1);
//        assertEquals(expResult, result, 0.00001);
//    }

//    @Test
//    public void testStddev_null() {
//        System.out.println("stddev_null");
//        int id = 0;
//        double expResult = 0;
//        double result = analyzer.stddev(id);
//        assertEquals(expResult, result, 0);
//    }

    /**
     * Test of percentile method, of class Analyzer.
     */
    @Test
    public void testPercentile() {
        System.out.println("percentile 20");
        int k = 20;
        double expResult = 64.0;
        double result = analyzer.percentile(timeMeasurements1, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
     public void testPercentile2() {
        System.out.println("percentile 50");
        int k = 50;
        double expResult = 77.0;
        double result = analyzer.percentile(timeMeasurements1, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile3() {
        System.out.println("percentile 0");
        int k = 0;
        double expResult = 43.0;
        double result = analyzer.percentile(timeMeasurements1, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile4() {
        System.out.println("percentile 100");
        int k = 100;
        double expResult = 99.0;
        double result = analyzer.percentile(timeMeasurements1, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentile_null() {
        System.out.println("percentile 20_null");
        int k = 20;
        double expResult = 0;
        double result = analyzer.percentile(null, k);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testPercentileIndex() {
        System.out.println("percentile 90");
        int k = 90;
        double expResult = 98.0;
        double result = analyzer.percentile(timeMeasurements1, k);
        assertEquals(expResult, result, 0.0);

    }

    @Test
    public void testPercentileIndex_null() {
        System.out.println("percentile 90_null");
        int k = 90;
        double expResult = 0;
        double result = analyzer.percentile(null, k);
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
        int[] result = analyzer.buckets(timeMeasurements1, NOFBuckets);
        System.out.println(Arrays.toString(result));
        for (int ind = 0; ind < expResult.length; ind++) {
            assertEquals(expResult[ind], result[ind], 0.00001);
        }
    }
    @Test
    public void testBuckets_null() {
        System.out.println("buckets_null");
        int NOFBuckets = 5;
        int[] expResult = {0, 0, 0, 0, 0};
        int[] result = analyzer.buckets(null, NOFBuckets);
        System.out.println(Arrays.toString(result));
        for (int ind = 0; ind < expResult.length; ind++) {
            assertEquals(expResult[ind], result[ind], 0.00001);
        }
    }

    @Test
    public void testSplitIntoBuckets() {
        List<TimeMeasurement> buckets;
        int bucketSize = 2;
        buckets = this.analyzer.splitIntoBuckets(timeMeasurements1, bucketSize);

        assertEquals(bucketSize, buckets.size());
        assertNull(buckets.get(1));

        bucketSize = 4;
        buckets = this.analyzer.splitIntoBuckets(timeMeasurements3, bucketSize);

        assertNotNull(buckets.get(0));
        assertNull(buckets.get(1));
        assertNull(buckets.get(2));
        assertNotNull(buckets.get(3));

        long duration = 0;
        int count = 0;
        for (TimeMeasurement t : timeMeasurements3) {
            DateTime time = new DateTime(t.getTimestamp());
            if (time.getYear() == 2013 && time.getMonthOfYear() == 6 ) {
                duration += converter.getDurationMillis(t.getDuration());
                count++;
            }
        }
        duration /= count;
        assertEquals(duration, converter.getDurationMillis(buckets.get(0).getDuration()));
    }

    @Test
    public void testSplitIntoBuckets_null() {
        List<TimeMeasurement> buckets;
        int bucketSize = 10;
        buckets = this.analyzer.splitIntoBuckets(null, bucketSize);
        assertEquals(bucketSize, buckets.size());
        for (TimeMeasurement bucket : buckets) {
            assertNull(bucket);
        }
    }
}