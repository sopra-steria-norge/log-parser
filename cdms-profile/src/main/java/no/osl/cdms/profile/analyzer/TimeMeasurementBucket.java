package no.osl.cdms.profile.analyzer;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;

import java.util.ArrayList;
import java.util.List;

public class TimeMeasurementBucket {

    Logger logger = Logger.getLogger(getClass().getName());

    private Procedure procedure;
    private DateTime timestamp;
    private long duration;

    private DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.123S");

    private List<TimeMeasurement> timeMeasurements;
    private boolean compressed = false;

    public TimeMeasurementBucket() {
        timeMeasurements = new ArrayList<TimeMeasurement>();
        compressed = false;
    }

    /**
     * Adds a TimeMeasurement to the bucket.
     * The TimeMeasurements are expected to have the same procedure.
     * @param timeMeasurementEntity
     */
    public void addTimeMeasurement(TimeMeasurement timeMeasurementEntity) {
        timeMeasurements.add(timeMeasurementEntity);
        compressed = false;
    }

    private void compress() {
        if (timeMeasurements == null || timeMeasurements.size() == 0) {
            return;
        }
        procedure = timeMeasurements.get(0).getProcedure();
        timestamp = new DateTime(timeMeasurements.get(0).getTimestamp());

        long durationSum = 0;
        for (TimeMeasurement timeMeasurement: timeMeasurements) {
            durationSum += converter.getDurationMillis(timeMeasurement.getDuration());
        }
        duration = durationSum / timeMeasurements.size();
        compressed = true;
    }

    public Procedure getProcedure() {
        if (!compressed) compress();
        return procedure;
    }

    public DateTime getTimestamp() {
        if (!compressed) compress();
        return timestamp;
    }

    public long getDuration() {
        if (!compressed) compress();
        return duration;
    }

    @Override
    public String toString() {
        return String.format("ProcedureID=%d, timestamp=%s, duration=%d", getProcedure().getId(), getTimestamp(), getDuration());
    }
}
