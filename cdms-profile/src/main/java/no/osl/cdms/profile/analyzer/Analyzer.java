/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import java.util.*;

import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public class Analyzer implements DataAnalyzer {
    private static DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");

    public Analyzer() {
    }

//    private void delegate(List<TimeMeasurement> times) {
//        if (times == null) {
//            times = Lists.newArrayList();
//        }
//        for (TimeMeasurement tm : times) {
//            map.put(tm.getProcedure().getId(), tm);
////            map.put(tm.getProcedure().getClassName(), tm);
////            map.put(tm.getProcedure().getClassName()+"."+tm.getProcedure().getMethod(), tm);
//        }
////        for (String key : map.keySet()) {
//        for (int key : map.keySet()) {
//            Collections.sort(new ArrayList(map.get(key)));
//        }
//    }

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

//    @Override
//    public double stddev(int id) {
//        if (col.isEmpty()) {
//            return 0;
//        }
//        double mean = average(id);
//        double stddevSum = 0;
//        for (TimeMeasurement tm : col) {
//            stddevSum += Math.pow(converter.getDurationMillis(tm.getDuration()) - mean, 2);
//        }
//        stddevSum /= map.get(id).size();
//        return Math.sqrt(stddevSum);
//    }

    @Override
    public double percentile(List<TimeMeasurement> timeMeasurements, int k) {
        if (timeMeasurements == null || timeMeasurements.isEmpty()) {
            return 0;
        }
        if (k == 0) return converter.getDurationMillis(timeMeasurements.get(0).getDuration());
        if (k == 100) return converter.getDurationMillis(timeMeasurements.get(timeMeasurements.size() - 1).getDuration());
        double ind = k / 100.0 * timeMeasurements.size();
        if (ind == (int) ind) {
            return (converter.getDurationMillis(timeMeasurements.get((int) ind).getDuration()) +
                    converter.getDurationMillis(timeMeasurements.get((int) (ind - 1)).getDuration())) / 2;
        } else {
            ind = Math.round(ind);
            return converter.getDurationMillis(timeMeasurements.get((int) (ind - 1)).getDuration());
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
