/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import no.osl.cdms.profile.analyzer.TimeMeasurementBucket;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author nutgaard
 */
@Component
public interface DataAnalyzer {
    public double average(int id);
    public double stddev(int id);
    public double percentile(int id, int percent);
    public int[] buckets(int id, int NOFBuckets);
    public List<TimeMeasurement> splitIntoBuckets(int id, int nBuckets);
}
