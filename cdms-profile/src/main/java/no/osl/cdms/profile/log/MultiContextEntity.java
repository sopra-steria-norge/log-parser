package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.MultiContext;

import javax.persistence.*;
import java.util.List;

/**
 * User: apalfi
 */
@Entity
@Table(name = "CDM_PROFILE_MULTICONTEXT")
public class MultiContextEntity implements MultiContext{

    @Column(name = "MULTICONTEXT_ID")
    @SequenceGenerator(name = "MULTICONTEXT_SEQ_GEN", sequenceName = "MULTICONTEXT_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTICONTEXT_SEQ_GEN")
    private int id;

    @Column(name = "START")
    private String start;

    @Column(name = "END")
    private String end;

    @OneToMany(mappedBy = "multiContext", targetEntity = TimeMeasurementEntity.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MultiContextEntity() {

    }

    public MultiContextEntity(String start, String end, List<TimeMeasurementEntity> timeMeasurements) {
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
