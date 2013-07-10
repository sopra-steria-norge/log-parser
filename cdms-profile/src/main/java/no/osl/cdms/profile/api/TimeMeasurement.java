package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.MultiContextEntity;

import java.util.Date;

public interface TimeMeasurement extends Comparable<TimeMeasurement>{

    int getId();

    void setId(int id);

    ProcedureEntity getProcedure();

    void setProcedure(ProcedureEntity measured);

    MultiContextEntity getMultiContext();

    void setMultiContext(MultiContextEntity multiContextMeasurement);

    Date getTimestamp();

    void setTimestamp(Date timestamp);

    String getDuration();

    void setDuration(String duration);
    
    @Override
    public boolean equals(Object obj);
    @Override
    public int hashCode();
}
