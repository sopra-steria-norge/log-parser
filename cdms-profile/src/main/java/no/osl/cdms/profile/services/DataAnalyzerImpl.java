package no.osl.cdms.profile.services;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.osl.cdms.profile.services.helpers.TimeMeasurementBucket;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.springframework.stereotype.Component;

@Component
public class DataAnalyzerImpl implements DataAnalyzer {

    private static final DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");
//    private static final Logger logger = Logger.getLogger(DataAnalyzerImpl.class);
    private static final Logger logger = Logger.getRootLogger();

    public DataAnalyzerImpl() {
    }

    @Override
    public double average(List<TimeMeasurement> timeMeasurements) {
        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (TimeMeasurement tm : timeMeasurements) {
            sum += converter.getDurationMillis(tm.getDuration());
        }
        return sum / timeMeasurements.size();
    }

    @Override
    public double percentile(List<TimeMeasurement> timeMeasurements, int k) {
        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return 0;
        }
        List<TimeMeasurement> timeMeasurementsCopy = Lists.newArrayList(timeMeasurements);
        Collections.sort(timeMeasurementsCopy);

        if (k == 0) {
            return converter.getDurationMillis(timeMeasurementsCopy.get(0).getDuration());
        }
        if (k == 100) {
            return converter.getDurationMillis(timeMeasurementsCopy.get(timeMeasurementsCopy.size() - 1).getDuration());
        }
        double ind = k / 100.0 * timeMeasurementsCopy.size();
        if (ind == (int) ind) {
            return (converter.getDurationMillis(timeMeasurementsCopy.get((int) ind).getDuration())
                    + converter.getDurationMillis(timeMeasurementsCopy.get((int) (ind) - 1).getDuration())) / 2;
        } else {
            ind = ((int) ind) + 1;
            return converter.getDurationMillis(timeMeasurementsCopy.get((int) (ind) - 1).getDuration());
        }
    }

    @Override
    public int[] buckets(List<TimeMeasurement> timeMeasurements, int nBuckets) {
        int[] out = new int[nBuckets];
        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return out;
        }
        double min = converter.getDurationMillis(timeMeasurements.get(0).getDuration());
        double bucketSize = (converter.getDurationMillis(timeMeasurements.get(timeMeasurements.size() - 1)
                .getDuration()) - min) / (nBuckets - 1);

        for (TimeMeasurement tm : timeMeasurements) {
            out[(int) ((converter.getDurationMillis(tm.getDuration()) - min) / bucketSize)]++;
        }
        return out;
    }

    @Override
    public List<TimeMeasurement> splitIntoBuckets(List<TimeMeasurement> timeMeasurements, DateTime fromDate, DateTime toDate, int nBuckets) {
        TimeMeasurement[] buckets = new TimeMeasurementBucket[nBuckets];

        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return Arrays.asList(buckets);
        }
        AnchorArray anchor = AnchorArray.createAnchors(timeMeasurements.get(0).getJodaTimestamp(), fromDate, toDate, nBuckets);

        int index;
        long first = anchor.anchorFromDate.getMillis();
        buckets = new TimeMeasurement[anchor.numberOfBucketsInDateRange];

        long milliesInMinutes = 60000;

        for (TimeMeasurement tm : timeMeasurements) {
            try {
                index = (int) ((tm.getTimestamp().getTime() - first) / (anchor.bucketSizeInMinutes * milliesInMinutes));
                logger.warn("TM: " + tm.getTimestamp().getTime() + " Anchor: " + first + " Buckets: " + anchor.bucketSizeInMinutes + " millies: " + milliesInMinutes + " INDEX: " + index);
            } catch (ArithmeticException e) {
                index = 0;
            }

            if (buckets[index] == null) {
                buckets[index] = new TimeMeasurementBucket();
            }
            ((TimeMeasurementBucket) buckets[index]).addTimeMeasurement(tm);
        }
        return Arrays.asList(buckets);
    }

    public static class AnchorArray {
        DateTime epochAnchor;
        long dateRange;
        int bucketSizeInMinutes;
        int firstAnchorDiff;
        int numberOfBucketsInDiff;
        DateTime anchorFromDate;
        int numberOfBucketsInDateRange;

        public static AnchorArray createAnchors(DateTime first, DateTime fromDate, DateTime toDate, int nBuckets) {
            AnchorArray aa = new AnchorArray();
          
            aa.epochAnchor = new DateTime(0); //1970 epoch
            aa.dateRange = Minutes.minutesBetween(fromDate, toDate).getMinutes(); //Eg 24*60
            
            aa.bucketSizeInMinutes = (int) Math.max(1, aa.dateRange / nBuckets);
            logger.error("minutesRange: " + aa.dateRange + " BucketSize: " + aa.bucketSizeInMinutes);

            aa.firstAnchorDiff = Minutes.minutesBetween(aa.epochAnchor, fromDate).getMinutes(); //Minutes passed since 1970 to fromDate
            aa.numberOfBucketsInDiff = (int) (aa.firstAnchorDiff / aa.bucketSizeInMinutes); //The number of buckets needed to get close to fromDate
            
            logger.error("AnchorDiff: " + aa.firstAnchorDiff + " BucketsInDiff: " + aa.numberOfBucketsInDiff);

            aa.anchorFromDate = aa.epochAnchor.plusMinutes(aa.numberOfBucketsInDiff * aa.bucketSizeInMinutes); //Anchored new fromDate
            logger.error("AnchorDate: " + aa.anchorFromDate + " ms: " + aa.anchorFromDate.getMillis());
             
            aa.numberOfBucketsInDateRange = (int) Math.ceil((aa.dateRange * 1.0) / aa.bucketSizeInMinutes);
            while (aa.anchorFromDate.plusMinutes(aa.numberOfBucketsInDateRange * aa.bucketSizeInMinutes).isBefore(toDate)) {
                aa.numberOfBucketsInDateRange++;
            }
            
            logger.error("BucketsInRange: " + aa.numberOfBucketsInDateRange);
            
            return aa;
        }

        @Override
        public String toString() {
            return "AnchorArray{" + "midnightAnchor=" + epochAnchor + ", dateRange=" + dateRange + ", bucketSizeInMinutes=" + bucketSizeInMinutes + ", firstAnchorDiff=" + firstAnchorDiff + ", numberOfBucketsInDiff=" + numberOfBucketsInDiff + ", anchorFromDate=" + anchorFromDate + ", numberOfBucketsInDateRange=" + numberOfBucketsInDateRange + '}';
        }
        
    }
}
