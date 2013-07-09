package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * User: apalfi
 */
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

    public List<MultiContext> getMultiContextsAfterTimestamp(String timestamp) {

        ArrayList<MultiContext> result = new ArrayList<MultiContext>();
        result.add(new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:16:18.847Z").toDate()));
        result.add(new MultiContextEntity(new DateTime("2013-06-25T01:15:52.600Z").toDate(),
                new DateTime("2013-06-25T01:16:20.847Z").toDate()));
        return result;

    }

    public Procedure getEqualProcedure(Procedure procedure) {
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
}
