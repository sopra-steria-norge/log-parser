package no.osl.cdms.profile.log;

import javax.persistence.*;
import java.util.List;

/**
 * User: apalfi
 */
@Entity
@Table(name = "MEASURED")
public class MeasuredEntity {

    @Column(name = "MEASURED_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLASS")
    private String className;

    @Column(name = "METHOD")
    private String method;

    @OneToMany(mappedBy = "measured", targetEntity = TimeMeasurementEntity.class, cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MeasuredEntity() {

    }

    public MeasuredEntity(int id, String name, String className, String method) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.method = method;
    }

    public MeasuredEntity(int id, String name, String className, String method,
                          List<TimeMeasurementEntity> timeMeasurements) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.method = method;
        this.timeMeasurements = timeMeasurements;
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
}