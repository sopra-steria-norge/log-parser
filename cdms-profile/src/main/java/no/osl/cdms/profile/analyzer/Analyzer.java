/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.interfaces.DataAnalyzer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import no.osl.cdms.profile.interfaces.TimeMeasurement;

/**
 *
 * @author nutgaard
 */
public class Analyzer implements DataAnalyzer {

    private Multimap<String, TimeMeasurement> map;

    public Analyzer(final List<TimeMeasurement> times) {
        this.map = ArrayListMultimap.create();
        delegate(times);
    }

    private void delegate(List<TimeMeasurement> times) {
        if (times == null) {
            times = Lists.newArrayList();
        }
        for (TimeMeasurement tm : times) {
            map.put("total", tm);
            map.put(tm.getName(), tm);
        }
        for (String key : map.keySet()) {
            Collections.sort(new ArrayList(map.get(key)));
        }
    }

    @Override
    public double average(String id) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (TimeMeasurement tm : col) {
            sum += tm.getTime();
        }
        return sum / col.size();
    }

    @Override
    public double stddev(String id) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        double mean = average(id);
        double stddevSum = 0;
        for (TimeMeasurement tm : col) {
            stddevSum += Math.pow(tm.getTime() - mean, 2);
        }
        stddevSum /= map.get(id).size();
        return Math.sqrt(stddevSum);
    }

    @Override
    public double percentile(String id, int k) {
        Collection<TimeMeasurement> col = map.get(id);
        if (col.isEmpty()) {
            return 0;
        }
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(col);
        double ind = k / 100.0 * tms.size();
        if (ind == (int) ind) {
            return (tms.get((int) ind).getTime() + tms.get((int) (ind - 1)).getTime()) / 2;
        } else {
            ind = Math.round(ind);
            return tms.get((int) (ind - 1)).getTime();
        }
    }

    @Override
    public int[] buckets(String id, int NOFBuckets) {
        Collection<TimeMeasurement> col = map.get(id);
        int[] out = new int[NOFBuckets];
        if (col.isEmpty()) {
            return out;
        }
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(col);
        double min = tms.get(0).getTime();
        double bucketSize = (tms.get(tms.size() - 1).getTime() - min) / (NOFBuckets - 1);

        for (TimeMeasurement tm : tms) {
            out[(int) ((tm.getTime() - min) / bucketSize)]++;
        }
        return out;
    }
}
