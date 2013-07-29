/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import no.osl.cdms.profile.web.helpers.TimeMeasurementBucket;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author nutgaard
 */
@Component
public interface DataAnalyzer {
    public double average(List<TimeMeasurement> timeMeasurements);
    //public double stddev(int id);
    public double percentile(List<TimeMeasurement> timeMeasurements, int percent);
    public int[] buckets(List<TimeMeasurement> timeMeasurements, int nBuckets);
    public List<TimeMeasurement> splitIntoBuckets(List<TimeMeasurement> timeMeasurements, int nBuckets);
}
