package no.osl.cdms.profile.log;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("L")
public class LapEntity extends TimeMeasurementEntity{

    @Column(name = "ID")
    private String id;

    public LapEntity() {

    }

    public LapEntity(String duration) {
        super(duration);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
