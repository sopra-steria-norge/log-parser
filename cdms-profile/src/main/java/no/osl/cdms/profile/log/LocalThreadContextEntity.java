package no.osl.cdms.profile.log;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("L")
public class LocalThreadContextEntity extends LogEntryEntity{

    @Column(name = "ID")
    private String id;
    @Column(name = "DURATION")
    private String duration;
    @Column(name = "CONTEXT")
    private String context;

    public LocalThreadContextEntity() {

    }

    public LocalThreadContextEntity(String id, String duration, String context) {
        this.id = id;
        this.duration = duration;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "id=" + id + ",duration=" + duration + ",context=" + context;
    }
}
