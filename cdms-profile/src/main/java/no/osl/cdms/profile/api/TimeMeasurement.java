package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.MeasuredEntity;
import no.osl.cdms.profile.log.MultiContextEntity;

/**
 * User: apalfi
 */
public interface TimeMeasurement {

    int getId();

    void setId(int id);

    MeasuredEntity getMeasured();

    void setMeasured(MeasuredEntity measured);

    MultiContextEntity getMultiContext();

    void setMultiContext(MultiContextEntity multiContextMeasurement);

    String getTimestamp();

    void setTimestamp(String timestamp);

    String getDuration();

    void setDuration(String duration);
}
