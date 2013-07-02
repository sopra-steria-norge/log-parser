package no.osl.cdms.profile.log;

import javax.persistence.*;

/**
 * User: apalfi
 */
@Entity
@Table(name = "TIMEMEASUREMENT")
public class TimeMeasurementEntity {

    @Column(name = "TIMEMEASUREMENT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "MEASURED_ID")
    private MeasuredEntity measured;

    @ManyToOne
    @JoinColumn(name = "MULTICONTEXT_MEASUREMENT_ID")
    private MultiContextMeasurementEntity multiContextMeasurement;

    @Column(name = "TIMESTAMP")
    private String timestamp;

    @Column(name = "DURATION")
    private String duration;

    public TimeMeasurementEntity() {

    }

    public TimeMeasurementEntity(int id, MeasuredEntity me, MultiContextMeasurementEntity mcme, String timestamp,
                                 String duration) {
        this.id = id;
        this.measured = me;
        this.multiContextMeasurement = mcme;
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

    public MultiContextMeasurementEntity getMultiContextMeasurement() {
        return multiContextMeasurement;
    }

    public void setMultiContextMeasurement(MultiContextMeasurementEntity multiContextMeasurement) {
        this.multiContextMeasurement = multiContextMeasurement;
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
