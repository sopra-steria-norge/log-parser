package no.osl.cdms.profile.log;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("W")
public class WaitEntity extends TimeMeasurementEntity{

    public WaitEntity() {

    }

    public WaitEntity(String duration) {
        super(duration);
    }
}
