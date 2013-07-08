package no.osl.cdms.profile.log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: apalfi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class EntitiesTest {

    @PersistenceContext
    private EntityManager entityManager;

    private ProcedureEntity procedureEntity;
    private MultiContextEntity multiContextEntity;
    private TimeMeasurementEntity timeMeasurementEntity, timeMeasurementEntity2;

    @Before
    public void before() {
        procedureEntity = new ProcedureEntity("name", "classname", "method");
        multiContextEntity = new MultiContextEntity("start", "end");
        timeMeasurementEntity = new TimeMeasurementEntity(procedureEntity, multiContextEntity, "timestamp", "duration");
        timeMeasurementEntity2 = new TimeMeasurementEntity(procedureEntity, multiContextEntity, "timestamp", "duration");

        entityManager.persist(procedureEntity);
        entityManager.persist(multiContextEntity);

    }

    @Test
    public void persisting_measured_should_cascade_persist() {
        ProcedureEntity measured = entityManager.find(ProcedureEntity.class, procedureEntity.getId());
        assertTrue(measured.getTimeMeasurements().size() == procedureEntity.getTimeMeasurements().size());
        assertEquals(measured.getTimeMeasurements().get(0), entityManager.find(TimeMeasurementEntity.class,
                measured.getTimeMeasurements().get(0).getId()));
    }

    @Test
    public void persisting_multicontext_should_cascade_persist() {
        MultiContextEntity multiContext = entityManager.find(MultiContextEntity.class, multiContextEntity.getId());
        assertTrue(multiContext.getTimeMeasurements().size() == multiContextEntity.getTimeMeasurements().size());
        assertEquals(timeMeasurementEntity2, entityManager.find(TimeMeasurementEntity.class,
                multiContext.getTimeMeasurements().get(1).getId()));
    }

}
