package no.osl.cdms.profile.log;

import javax.persistence.*;

/**
 * User: apalfi
 */
@Entity
@Table(name = "CDM_PROFILE_TIMEMEASUREMENT")
public class TimeMeasurementEntity {

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
        this.measured = me;
        this.multiContext = mcme;
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
    }

    public MultiContextEntity getMultiContext() {
        return multiContext;
    }

    public void setMultiContext(MultiContextEntity multiContextMeasurement) {
        this.multiContext = multiContextMeasurement;
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


}
