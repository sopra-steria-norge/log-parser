/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;

/**
 *
 * @author nutgaard
 */
public class Analyzer implements DataAnalyzer {
    private static DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");
    private Multimap<Integer, TimeMeasurement> map;

    public Analyzer(final List<TimeMeasurement> times) {
        this.map = ArrayListMultimap.create();
        delegate(times);
    }

    private void delegate(List<TimeMeasurement> times) {
        if (times == null) {
            times = Lists.newArrayList();
        }
        for (TimeMeasurement tm : times) {
            map.put(tm.getProcedure().getId(), tm);
//            map.put(tm.getProcedure().getClassName(), tm);
//            map.put(tm.getProcedure().getClassName()+"."+tm.getProcedure().getMethod(), tm);
        }
//        for (String key : map.keySet()) {
        for (int key : map.keySet()) {
            Collections.sort(new ArrayList(map.get(key)));
        }
    }

    @Override
    public double average(int id) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (TimeMeasurement tm : col) {
            sum += converter.getDurationMillis(tm.getDuration());
        }
        return sum / col.size();
    }

    @Override
    public double stddev(int id) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        double mean = average(id);
        double stddevSum = 0;
        for (TimeMeasurement tm : col) {
            stddevSum += Math.pow(converter.getDurationMillis(tm.getDuration()) - mean, 2);
        }
        stddevSum /= map.get(id).size();
        return Math.sqrt(stddevSum);
    }

    @Override
    public double percentile(int id, int k) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(col);
        if (k == 0) return converter.getDurationMillis(tms.get(0).getDuration());
        if (k == 100) return converter.getDurationMillis(tms.get(tms.size() - 1).getDuration());
        double ind = k / 100.0 * tms.size();
        if (ind == (int) ind) {
            return (converter.getDurationMillis(tms.get((int) ind).getDuration()) + converter.getDurationMillis(tms.get((int) (ind - 1)).getDuration())) / 2;
        } else {
            ind = Math.round(ind);
            return converter.getDurationMillis(tms.get((int) (ind - 1)).getDuration());
        }
    }

    @Override
    public int[] buckets(int id, int NOFBuckets) {
        Collection<TimeMeasurement> col = map.get(id);
        int[] out = new int[NOFBuckets];
        if (col.isEmpty()) {
            return out;
        }
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(col);
        double min = converter.getDurationMillis(tms.get(0).getDuration());
        double bucketSize = (converter.getDurationMillis(tms.get(tms.size() - 1).getDuration()) - min) / (NOFBuckets - 1);

        for (TimeMeasurement tm : tms) {
            out[(int) ((converter.getDurationMillis(tm.getDuration()) - min) / bucketSize)]++;
        }
        return out;
    }

    @Override
    public TimeMeasurementBucket[] splitIntoBuckets(int id, int nBuckets) {
        ArrayList<TimeMeasurement> timeMeasurements = new ArrayList(map.get(id));
        TimeMeasurementBucket[] buckets;

        buckets = new TimeMeasurementBucket[nBuckets];

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

            if (buckets[index] == null) buckets[index] = new TimeMeasurementBucket();
            buckets[index].addTimeMeasurement(tm);
        }
        return buckets;
    }
}
