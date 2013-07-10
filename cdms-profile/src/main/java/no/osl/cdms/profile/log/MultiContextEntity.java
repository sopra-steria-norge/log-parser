package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.MultiContext;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CDM_PROFILE_MULTICONTEXT")
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

    public MultiContextEntity() {

    }

    public MultiContextEntity(Date start, Date end) {
        this.start = start;
        this.end = end;
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
}
