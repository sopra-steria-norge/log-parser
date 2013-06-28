package no.osl.cdms.profile.log;

import javax.persistence.*;

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE")
@Table(name="CDM_LOG_TIME_MEASUREMENT")
public class TimeMeasurementEntity {
    @Column(name = "DURATION")
    protected String duration;

    @Column(name = "TM_ID")
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long timeMeasurementId;

    @ManyToOne
    @JoinColumn(name = "LOG_ENTRY_ID")
    private MultiThreadContextEntity multiThreadContext;

    public TimeMeasurementEntity() {

    }

    public TimeMeasurementEntity(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getTimeMeasurementId() {
        return timeMeasurementId;
    }

    public void setTimeMeasurementId(long timeMeasurementId) {
        this.timeMeasurementId = timeMeasurementId;
    }

    public MultiThreadContextEntity getMultiThreadContext() {
        return multiThreadContext;
    }

    public void setMultiThreadContext(MultiThreadContextEntity multiThreadContext) {
        this.multiThreadContext = multiThreadContext;
    }
}
