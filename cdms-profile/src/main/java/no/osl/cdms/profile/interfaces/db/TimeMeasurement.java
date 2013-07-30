package no.osl.cdms.profile.interfaces.db;

import java.util.Date;
import no.osl.cdms.profile.persistence.MultiContextEntity;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;

public interface TimeMeasurement extends Comparable<TimeMeasurement>{

    int getId();

    void setId(int id);

    @JsonIgnore
    ProcedureEntity getProcedure();

    int getProcedureId();

    void setProcedure(ProcedureEntity measured);

    @JsonIgnore
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
