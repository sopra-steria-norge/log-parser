package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        return getTimeMeasurementsByProcedure(fromDate, toDate, procedure, null);
    }

    public List<TimeMeasurement> getTimeMeasurementsByProcedure(DateTime fromDate, DateTime toDate, Procedure procedure, TimeMeasurement.Field orderBy) {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery("SELECT a FROM TimeMeasurementEntity a " +
                "where a.procedure = :procedure AND a.timestamp >= :fromDate AND a.timestamp <= :toDate"
                + queryOrderingSuffix(orderBy), TimeMeasurement.class);
        query.setParameter("procedure", procedure);
        query.setParameter("fromDate", fromDate.toDate());
        query.setParameter("toDate", toDate.toDate());

        return query.getResultList();
    }

    public List<TimeMeasurement> getTimeMeasurementsByProcedure(Procedure procedure) {
        return getTimeMeasurementsByProcedure(procedure, null);
    }

    public List<TimeMeasurement> getTimeMeasurementsByProcedure(Procedure procedure, TimeMeasurement.Field orderBy) {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery(
                "SELECT a FROM TimeMeasurementEntity a where a.procedure = :procedure" + queryOrderingSuffix(orderBy),
                TimeMeasurement.class);
        query.setParameter("procedure", procedure);

        return query.getResultList();
    }

    public Procedure getEqualPersistedProcedure(Procedure procedure) {
        TypedQuery<ProcedureEntity> query = entityManager.createQuery(
                "SELECT a FROM ProcedureEntity a where a.className = :class AND " +
                        "a.method = :method", ProcedureEntity.class);
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

    public TimeMeasurement getLatestTimeMeasurement() {
        TypedQuery<TimeMeasurement> query = entityManager.createQuery(
                "SELECT a FROM TimeMeasurementEntity a where a.timestamp = (SELECT MAX(b.timestamp) from TimeMeasurementEntity b)",
                TimeMeasurement.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Generates a String to be appended to a query.
     * The String will specify which field to order by, if any.
     * @param orderBy
     * @return query suffix
     */
    private String queryOrderingSuffix(TimeMeasurement.Field orderBy) {
        String querySuffix = "";
        if (orderBy == null) {
            return querySuffix;
        }
        switch (orderBy) {
            case DURATION:
                querySuffix = " ORDER BY a.duration";
                break;
            case TIMESTAMP:
                querySuffix = " ORDER BY a.timestamp";
                break;
        }
        return querySuffix;
    }


}
