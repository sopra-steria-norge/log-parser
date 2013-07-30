
package no.osl.cdms.profile.interfaces;

import java.util.List;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import org.springframework.stereotype.Component;

@Component
public interface DataAnalyzer {
    public double average(List<TimeMeasurement> timeMeasurements);
    public double percentile(List<TimeMeasurement> timeMeasurements, int percent);
    public int[] buckets(List<TimeMeasurement> timeMeasurements, int nBuckets);
    public List<TimeMeasurement> splitIntoBuckets(List<TimeMeasurement> timeMeasurements, int nBuckets);
}
