package no.osl.cdms.profile.log;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("T")
public class TotalEntity extends TimeMeasurementEntity {

    @Column(name = "ID")
    private String id;
    @Column(name = "START")
    private String start;
    @Column(name = "END")
    private String end;

    public TotalEntity() {

    }

    public TotalEntity(String id, String duration, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
