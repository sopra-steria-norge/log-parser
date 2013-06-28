package no.osl.cdms.profile.log;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("M")
public class MultiThreadContextEntity extends LogEntryEntity{

    @Column(name = "TOTAL")
    private TotalEntity total;
    @OneToMany(mappedBy = "multiThreadContext",
            targetEntity = TimeMeasurementEntity.class,
            cascade = CascadeType.ALL)
    private List<TimeMeasurementEntity> timeMeasurements;

    public MultiThreadContextEntity() {

    }

    public MultiThreadContextEntity(TotalEntity total, List<TimeMeasurementEntity> timeMeasurements) {
        this.total = total;
        this.timeMeasurements = timeMeasurements;
    }

    public TotalEntity getTotal() {
        return total;
    }

    public void setTotal(TotalEntity total) {
        this.total = total;
    }

    public List<TimeMeasurementEntity> getTimeMeasurements() {
        return timeMeasurements;
    }

    public void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements) {
        this.timeMeasurements = timeMeasurements;
    }

    @Override
    public String toString() {
        String returnString = "Total{" + total + "}";
        for (TimeMeasurementEntity timeMeasurement : timeMeasurements) {
            returnString += "," + timeMeasurement;
        }
        return returnString;
    }
}
