package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

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


    public List<TimeMeasurement> getTimeMeasurementsByProcedure(Date fromDate, Date toDate, Procedure procedure) {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery(
                "SELECT a FROM TimeMeasurementEntity a where a.procedure = :procedure AND a.timestamp >= :fromDate" +
                        " AND a.timestamp <= :toDate", TimeMeasurement.class);
        query.setParameter("procedure", procedure);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);

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
            return null;
        }

    }
}
