/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.models.TimeMeasurement;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private void delegate(final List<TimeMeasurement> times) {
        for (TimeMeasurement tm : times) {
            map.put("total", tm);
            map.put(tm.name, tm);
        }
        for (String key : map.keySet()) {
            Collections.sort(new ArrayList(map.get(key)));
        }
    }

    @Override
    public double average(String id) {
        int counter = 0;
        double sum = 0;
        for (TimeMeasurement tm : map.get(id)) {
            sum += tm.time;
            counter++;
        }
        if (counter == 0) {
            return 0;
        }
        return sum / counter;
    }

    @Override
    public double stddev(String id) {
        double mean = average(id);
        double stddevSum = 0;
        for (TimeMeasurement tm : map.get(id)) {
            stddevSum += Math.pow(tm.time - mean, 2);
        }
        stddevSum /= map.get(id).size();
        return Math.sqrt(stddevSum);
    }

    @Override
    public double percentile(String id, int k) {
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(map.get(id));
        double ind = k / 100.0 * tms.size();
        if (ind == (int) ind) {
            return (tms.get((int) ind).time + tms.get((int) (ind - 1)).time) / 2;
        } else {
            ind = Math.round(ind);
            return tms.get((int) (ind - 1)).time;
        }
    }

    @Override
    public double[] buckets(String id, int NOFBuckets) {
        List<TimeMeasurement> tms = new ArrayList<TimeMeasurement>(map.get(id));
        double[] out = new double[NOFBuckets];
        double min = tms.get(0).time;
        double bucketSize = (tms.get(tms.size()-1).time-min)/(NOFBuckets-1);
        
        for (TimeMeasurement tm : tms) {
            out[(int)((tm.time-min)/bucketSize)]++;
        }
        return out;
    }
}
