package no.osl.cdms.profile.interfaces.db;

import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.MultiContextEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

import java.util.Date;

public interface TimeMeasurement extends Comparable<TimeMeasurement>{

    int getId();

    void setId(int id);

    ProcedureEntity getProcedure();

    void setProcedure(ProcedureEntity measured);

    MultiContextEntity getMultiContext();

    void setMultiContext(MultiContextEntity multiContextMeasurement);

    @JsonIgnore
    Date getTimestamp();

    DateTime getJodaTimestamp();

    void setTimestamp(Date timestamp);

    String getDuration();

    void setDuration(String duration);
    
    @Override
    public boolean equals(Object obj);
    @Override
    public int hashCode();

    public enum Field {
        ID, PROCEDURE, MULTI_CONTEXT, TIMESTAMP, DURATION;
    }
}
