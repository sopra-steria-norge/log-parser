package no.osl.cdms.profile.log;

import com.google.common.collect.Lists;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;
import java.util.List;

/**
 * User: apalfi
 */
@Entity
@Table(name = "CDM_PROFILE_MULTICONTEXT")
@XmlRootElement
public class MultiContextEntity implements MultiContext{

    @Column(name = "MULTICONTEXT_ID")
    @SequenceGenerator(name = "MULTICONTEXT_SEQ_GEN", sequenceName = "MULTICONTEXT_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MULTICONTEXT_SEQ_GEN")
    private int id;

    @Column(name = "START_TIME")
    private Date start;

    @Column(name = "END_TIME")
    private Date end;

    @XmlTransient
    @OneToMany(mappedBy = "multiContext", targetEntity = TimeMeasurementEntity.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MultiContextEntity() {

    }

    public MultiContextEntity(Date start, Date end) {
        this.start = start;
        this.end = end;
        this.timeMeasurements = Lists.newLinkedList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<TimeMeasurementEntity> getTimeMeasurements() {
        return timeMeasurements;
    }

    public void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements) {
        this.timeMeasurements = timeMeasurements;
    }

    public void addTimeMeasurement(TimeMeasurement timeMeasurement) {
        timeMeasurement.setMultiContext(this);
    }
}
