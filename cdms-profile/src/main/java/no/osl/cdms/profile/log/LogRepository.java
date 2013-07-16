package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class LogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void persistNewProcedure(Procedure procedure) {
        entityManager.persist((ProcedureEntity)procedure);
    }

    public void persistNewMultiContext(MultiContext multiContext) {
        entityManager.persist((MultiContextEntity)multiContext);
    }

    public void persistNewTimeMeasurement(TimeMeasurement timeMeasurement) {
        entityManager.persist((TimeMeasurementEntity)timeMeasurement);
    }

    public ProcedureEntity getProcedure(int id) {
        return entityManager.find(ProcedureEntity.class, id);
    }

    public MultiContextEntity getMultiContext(int id) {
        return entityManager.find(MultiContextEntity.class, id);
    }

    public TimeMeasurementEntity getTimeMeasurement(int id) {
        return entityManager.find(TimeMeasurementEntity.class, id);
    }


    public List<TimeMeasurement> getTimeMeasurementsByProcedure(DateTime fromDate, DateTime toDate, Procedure procedure) {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery(
                "SELECT a FROM TimeMeasurementEntity a where a.procedure = :procedure AND a.timestamp >= :fromDate" +
                        " AND a.timestamp <= :toDate", TimeMeasurement.class);
        query.setParameter("procedure", procedure);
        query.setParameter("fromDate", fromDate.toDate());
        query.setParameter("toDate", toDate.toDate());

        return query.getResultList();
    }

    public List<TimeMeasurement> getTimeMeasurementsByProcedure(Procedure procedure) {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery(
                "SELECT a FROM TimeMeasurementEntity a where a.procedure = :procedure",
                TimeMeasurement.class);
        query.setParameter("procedure", procedure);

        return query.getResultList();
    }

    public Procedure getEqualPersistedProcedure(Procedure procedure) {
        TypedQuery<ProcedureEntity> query = entityManager.createQuery(
                "SELECT a FROM ProcedureEntity a where a.name = :name AND " +
                        "a.className = :class AND " +
                        "a.method = :method", ProcedureEntity.class);
        query.setParameter("name", procedure.getName());
        query.setParameter("class", procedure.getClassName());
        query.setParameter("method", procedure.getMethod());
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }

    public List<ProcedureEntity> getAllProcedures() {
        TypedQuery<ProcedureEntity> query = entityManager.createQuery(
                "SELECT a FROM ProcedureEntity a", ProcedureEntity.class);
        try {
            return query.getResultList();
        } catch (javax.persistence.NoResultException e) {
            return new java.util.ArrayList<ProcedureEntity>();
        }

    }

    public List<String> getAllLayoutEntityNames() {
        List<LayoutEntity> layoutEntities = getAllLayoutEntities();
        List<String> names = new ArrayList<String>();

        for (LayoutEntity layoutEntity: layoutEntities) {
            names.add(layoutEntity.getName());
        }

        return names;
    }

    public List<LayoutEntity> getAllLayoutEntities() {
        TypedQuery<LayoutEntity> query = entityManager.createQuery(
                "SELECT a FROM LayoutEntity a", LayoutEntity.class);
        try {
            return query.getResultList();
        } catch (javax.persistence.NoResultException e) {
            return new java.util.ArrayList<LayoutEntity>();
        }
    }

    public LayoutEntity getLayoutEntity(String name) {
        TypedQuery<LayoutEntity> query = entityManager.createQuery(
                "SELECT a FROM LayoutEntity a WHERE a.name = :name", LayoutEntity.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
    }
}
