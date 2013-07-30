package no.osl.cdms.profile.services;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.osl.cdms.profile.services.helpers.TimeMeasurementBucket;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;

import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.springframework.stereotype.Component;

@Component
public class DataAnalyzerImpl implements DataAnalyzer {
    private static DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");

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
    public List<TimeMeasurement> splitIntoBuckets(List<TimeMeasurement> timeMeasurements, int nBuckets) {
        TimeMeasurement[] buckets = new TimeMeasurementBucket[nBuckets];

        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return Arrays.asList(buckets);
        }

        long first = timeMeasurements.get(0).getTimestamp().getTime();
        long last = timeMeasurements.get(timeMeasurements.size()-1).getTimestamp().getTime();
        double timeInterval = (double)((last - first + 1)) / (double)nBuckets;
        int index;

        for (TimeMeasurement tm : timeMeasurements) {
            try {
                index = (int) ((tm.getTimestamp().getTime() - first) / timeInterval);
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