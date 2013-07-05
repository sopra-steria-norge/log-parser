package no.osl.cdms.profile.log;

import no.osl.cdms.profile.api.Measured;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
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

    public void saveMeasured(MeasuredEntity measuredEntity) {
        entityManager.persist(measuredEntity);
    }

    public void saveMultiContext(MultiContextEntity multiContextEntity) {
        entityManager.persist(multiContextEntity);
    }

    public void saveTimeMeasurement(TimeMeasurementEntity timeMeasurementEntity) {
        entityManager.persist(timeMeasurementEntity);
    }

    public MeasuredEntity getMeasured(int id) {
        return entityManager.find(MeasuredEntity.class, id);
    }

    public MultiContextEntity getMultiContext(int id) {
        return entityManager.find(MultiContextEntity.class, id);
    }

    public TimeMeasurementEntity getTimeMeasurement(int id) {
        return entityManager.find(TimeMeasurementEntity.class, id);
    }

    public List<MultiContext> getMultiContextsAfterTimestamp(String timestamp) {

        ArrayList<MultiContext> result = new ArrayList<MultiContext>();
        result.add(new MultiContextEntity("2013-06-25T01:15:52.458Z", "2013-06-25T01:16:18.847Z"));
        result.add(new MultiContextEntity("2013-06-25T01:15:52.600Z", "2013-06-25T01:16:20.847Z"));
        return result;

    }

    public Measured getEqualMeasured(Measured measured) {
        TypedQuery<MeasuredEntity> query = entityManager.createQuery(
                "SELECT a FROM MeasuredEntity a where a.name = :name AND" +
                        "a.class = :class AND" +
                        "a.method = :method", MeasuredEntity.class);
        query.setParameter("name", measured.getName());
        query.setParameter("class", measured.getClassName());
        query.setParameter("method", measured.getMethod());
        MeasuredEntity result = query.getSingleResult();
        return result;
    }
}
