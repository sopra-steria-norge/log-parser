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
import org.joda.time.Days;
import org.joda.time.Minutes;

import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.springframework.stereotype.Component;

@Component
public class DataAnalyzerImpl implements DataAnalyzer {
    private static final DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");
    private static final Logger logger = Logger.getLogger(DataAnalyzerImpl.class);

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
            return (converter.getDurationMillis(timeMeasurementsCopy.get((int) ind).getDuration()) +
                    converter.getDurationMillis(timeMeasurementsCopy.get((int) (ind) - 1).getDuration())) / 2;
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
        DateTime midnightAnchor = fromDate.withTime(0, 0, 0, 0);
        DateTime firstTimemeasurement = timeMeasurements.get(0).getJodaTimestamp();
        
        int dateRange = Minutes.minutesBetween(fromDate, toDate).getMinutes();
        int bucketSizeInMinutes = Math.max(1, dateRange/nBuckets);
        logger.warn("minutesRange: "+dateRange+" BucketSize: "+bucketSizeInMinutes);
        
        int firstAnchorDiff = Minutes.minutesBetween(midnightAnchor, firstTimemeasurement).getMinutes();
        int numberOfBucketsInDiff = firstAnchorDiff/bucketSizeInMinutes;
        logger.warn("AnchorDiff: "+firstAnchorDiff+" BucketsInDiff: "+numberOfBucketsInDiff);
        
        DateTime anchorFromDate = midnightAnchor.plusMinutes(numberOfBucketsInDiff*bucketSizeInMinutes);
        logger.warn("AnchorDate: "+anchorFromDate+" ms: "+anchorFromDate.getMillis());
        int numberOfBucketsInDateRange = (int)Math.ceil((dateRange*1.0) /bucketSizeInMinutes);
        logger.warn("BucketsInRange: "+numberOfBucketsInDateRange);
        
        int index;
        long first = anchorFromDate.getMillis();
        buckets = new TimeMeasurement[numberOfBucketsInDateRange];
        
        int milliesInMinutes = 60000;
        
        for (TimeMeasurement tm : timeMeasurements) {
            try {
                logger.warn("TM: "+tm.getTimestamp()+" ms: "+tm.getTimestamp().getTime()+" Anchor: "+first);
                index = (int) ((tm.getTimestamp().getTime() - first) / (bucketSizeInMinutes*milliesInMinutes));
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
}
