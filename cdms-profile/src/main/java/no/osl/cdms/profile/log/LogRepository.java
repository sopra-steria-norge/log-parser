package no.osl.cdms.profile.log;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
