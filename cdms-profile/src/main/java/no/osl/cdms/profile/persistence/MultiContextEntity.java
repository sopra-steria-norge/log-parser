package no.osl.cdms.profile.persistence;

import java.util.Date;
import java.util.logging.Logger;
import javax.persistence.*;
import no.osl.cdms.profile.interfaces.db.MultiContext;

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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Date getStart() {
        return start;
    }

    @Override
    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(Date end) {
        this.end = end;
    }
}
