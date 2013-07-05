package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.TimeMeasurement;

import javax.persistence.*;
import org.joda.time.convert.Converter;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;

/**
 * User: apalfi
 */
@Entity
@Table(name = "CDM_PROFILE_TIMEMEASUREMENT")
public class TimeMeasurementEntity implements TimeMeasurement {

    @Column(name = "TIMEMEASUREMENT_ID")
    @SequenceGenerator(name = "TIMEMEASUREMENT_SEQ_GEN", sequenceName = "TIMEMEASUREMENT_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIMEMEASUREMENT_SEQ_GEN")
    private int id;

    @ManyToOne
    @JoinColumn(name = "MEASURED_ID")
    private MeasuredEntity measured;

    @ManyToOne
    @JoinColumn(name = "MULTICONTEXT_ID")
    private MultiContextEntity multiContext;

    @Column(name = "TIMESTAMP")
    private String timestamp;

    @Column(name = "DURATION")
    private String duration;

    public TimeMeasurementEntity() {

    }

    public TimeMeasurementEntity(MeasuredEntity me, MultiContextEntity mcme, String timestamp,
                                 String duration) {
        setMeasured(me);
        setMultiContext(mcme);
        this.timestamp = timestamp;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MeasuredEntity getMeasured() {
        return measured;
    }

    public void setMeasured(MeasuredEntity measured) {
        this.measured = measured;
        measured.getTimeMeasurements().add(this);
    }

    public MultiContextEntity getMultiContext() {
        return multiContext;
    }

    public void setMultiContext(MultiContextEntity multiContextMeasurement) {
        this.multiContext = multiContextMeasurement;
        if (multiContextMeasurement != null)
            multiContextMeasurement.getTimeMeasurements().add(this);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(TimeMeasurement o) {
        DurationConverter c = ConverterManager.getInstance().getDurationConverter(this.getDuration());
        return (int)Math.signum(c.getDurationMillis(this.getDuration())-c.getDurationMillis(o.getDuration()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + (this.measured != null ? this.measured.hashCode() : 0);
        hash = 23 * hash + (this.multiContext != null ? this.multiContext.hashCode() : 0);
        hash = 23 * hash + (this.timestamp != null ? this.timestamp.hashCode() : 0);
        hash = 23 * hash + (this.duration != null ? this.duration.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeMeasurementEntity other = (TimeMeasurementEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.measured != other.measured && (this.measured == null || !this.measured.equals(other.measured))) {
            return false;
        }
        if (this.multiContext != other.multiContext && (this.multiContext == null || !this.multiContext.equals(other.multiContext))) {
            return false;
        }
        if ((this.timestamp == null) ? (other.timestamp != null) : !this.timestamp.equals(other.timestamp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TimeMeasurementEntity{" + "id=" + id + ", measured=" + measured + ", multiContext=" + multiContext + ", timestamp=" + timestamp + ", duration=" + duration + '}';
    }
    
    
}
