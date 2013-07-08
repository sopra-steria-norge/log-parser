package no.osl.cdms.profile.log;

import com.google.common.collect.Lists;
import no.osl.cdms.profile.api.Measured;
import no.osl.cdms.profile.api.TimeMeasurement;

import javax.persistence.*;
import java.util.List;

/**
 * User: apalfi
 */
@Entity
@Table(name = "CDM_PROFILE_MEASURED", uniqueConstraints={@UniqueConstraint(columnNames={"CLASS", "METHOD"})})
public class MeasuredEntity implements Measured {

    @Column(name = "MEASURED_ID")
    @SequenceGenerator(name = "MEASURED_SEQ_GEN", sequenceName = "MEASURED_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEASURED_SEQ_GEN")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLASS")
    private String className;

    @Column(name = "METHOD")
    private String method;

    @OneToMany(mappedBy = "measured", targetEntity = TimeMeasurementEntity.class, fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MeasuredEntity() {

    }

    public MeasuredEntity(String name, String className, String method) {
        this.name = name;
        this.className = className;
        this.method = method;
        this.timeMeasurements = Lists.newLinkedList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<TimeMeasurementEntity> getTimeMeasurements() {
        return timeMeasurements;
    }

    public void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements) {
        this.timeMeasurements = timeMeasurements;
    }

    @Override
    public String toString() {
        return "MeasuredEntity{" + "id=" + id + ", name=" + name + ", className=" + className + ", method=" + method + ", timeMeasurements=" + timeMeasurements.size() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.className != null ? this.className.hashCode() : 0);
        hash = 97 * hash + (this.method != null ? this.method.hashCode() : 0);
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
        final MeasuredEntity other = (MeasuredEntity) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className)) {
            return false;
        }
        if ((this.method == null) ? (other.method != null) : !this.method.equals(other.method)) {
            return false;
        }
        return true;
    }
    

    public void addTimeMeasurement(TimeMeasurement timeMeasurement) {
        timeMeasurement.setMeasured(this);
    }
}