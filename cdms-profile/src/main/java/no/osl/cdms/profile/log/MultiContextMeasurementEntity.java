package no.osl.cdms.profile.log;

import javax.persistence.*;
import java.util.List;

/**
 * User: apalfi
 */
@Entity
@Table(name = "MULTICONTEXT_MEASUREMENT")
public class MultiContextMeasurementEntity {

    @Column(name = "MULTICONTEXT_MEASUREMENT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "START")
    private String start;

    @Column(name = "END")
    private String end;

    @OneToMany(mappedBy = "multiContextMeasurement", targetEntity = TimeMeasurementEntity.class, cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MultiContextMeasurementEntity() {

    }

    public MultiContextMeasurementEntity(int id, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public MultiContextMeasurementEntity(int id, String start, String end, List<TimeMeasurementEntity> timeMeasurements) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.timeMeasurements = timeMeasurements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<TimeMeasurementEntity> getTimeMeasurements() {
        return timeMeasurements;
    }

    public void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements) {
        this.timeMeasurements = timeMeasurements;
    }
}
