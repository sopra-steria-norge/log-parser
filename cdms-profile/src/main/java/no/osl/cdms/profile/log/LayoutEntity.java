package no.osl.cdms.profile.log;

import javax.persistence.*;

@Entity
@Table(name = "CDM_PROFILE_LAYOUT")
public class LayoutEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "JSON")
    private String json;

    public LayoutEntity() {

    }

    public LayoutEntity(String name, String json) {
        this.name = name;
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LayoutEntity other = (LayoutEntity) obj;
        if (this.name != other.name && (this.name == null || !this.name.equals(other.name))) {
            return false;
        }
        if (this.json != other.json && (this.json == null || !this.json.equals(other.json))) {
            return false;
        }
        return true;
    }


    public String toString() {
        return String.format("LayoutEntity: {Name: '%s', JSON: '%s'}", name, json);
    }
}
