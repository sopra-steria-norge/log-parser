package no.osl.cdms.profile.persistence;

import java.util.logging.Logger;
import javax.persistence.*;
import no.osl.cdms.profile.interfaces.db.Procedure;

@Entity
@Table(name = "CDM_PROFILE_PROCEDURE")
public class ProcedureEntity implements Procedure {

    @Column(name = "PROCEDURE_ID")
    @SequenceGenerator(name = "PROCEDURE_SEQ_GEN", sequenceName = "PROCEDURE_SEQ")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROCEDURE_SEQ_GEN")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CLASS")
    private String className;

    @Column(name = "METHOD")
    private String method;

    public ProcedureEntity() {

    }

    public ProcedureEntity(String name, String className, String method) {
        this.name = name;
        this.className = className;
        this.method = method;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ProcedureEntity{" + "id=" + id + ", name=" + name + ", className=" + className + ", method=" + method + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (this.className != null ? this.className.hashCode() : 0);
        hash = 29 * hash + (this.method != null ? this.method.hashCode() : 0);
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
        final ProcedureEntity other = (ProcedureEntity) obj;
        
        if ((this.className == null) ? (other.className != null) : !this.className.equals(other.className)) {
            return false;
        }
        if ((this.method == null) ? (other.method != null) : !this.method.equals(other.method)) {
            return false;
        }
        return true;
    }
    private static final Logger LOG = Logger.getLogger(ProcedureEntity.class.getName());
    

    
}