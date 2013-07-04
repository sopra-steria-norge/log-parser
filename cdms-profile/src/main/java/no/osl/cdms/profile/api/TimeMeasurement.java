package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.MeasuredEntity;
import no.osl.cdms.profile.log.MultiContextEntity;

/**
 * User: apalfi
 */
public interface TimeMeasurement extends Comparable<TimeMeasurement>{

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
    
    @Override
    public boolean equals(Object obj);
    @Override
    public int hashCode();
}
