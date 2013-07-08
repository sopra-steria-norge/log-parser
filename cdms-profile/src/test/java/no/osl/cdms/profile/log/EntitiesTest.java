package no.osl.cdms.profile.log;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
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
        multiContextEntity = new MultiContextEntity(new DateTime("2013-06-25T01:16:18.847Z").toDate(),
                new DateTime("2013-06-25T01:16:18.900Z").toDate());
        timeMeasurementEntity = new TimeMeasurementEntity(procedureEntity, multiContextEntity,
                new DateTime("2013-06-25T01:16:18.847Z").toDate(), "PT0.017S");
        timeMeasurementEntity2 = new TimeMeasurementEntity(procedureEntity, multiContextEntity,
                new DateTime("2013-06-25T01:16:18.847Z").toDate(), "PT0.017S");

        entityManager.persist(procedureEntity);
        entityManager.persist(multiContextEntity);

    }

    @Test
    public void persisting_procedure_should_cascade_persist() {
        System.out.println("persisting_procedure_should_cascade_persist");
        ProcedureEntity procedure = entityManager.find(ProcedureEntity.class, procedureEntity.getId());
        assertTrue(procedure.getTimeMeasurements().size() == procedureEntity.getTimeMeasurements().size());
        assertEquals(procedure.getTimeMeasurements().get(0), entityManager.find(TimeMeasurementEntity.class,
                procedure.getTimeMeasurements().get(0).getId()));
    }

    @Test
    public void persisting_multicontext_should_cascade_persist() {
        System.out.println("persisting_multicontext_should_cascade_persist");
        MultiContextEntity multiContext = entityManager.find(MultiContextEntity.class, multiContextEntity.getId());
        assertTrue(multiContext.getTimeMeasurements().size() == multiContextEntity.getTimeMeasurements().size());
        assertEquals(timeMeasurementEntity2, entityManager.find(TimeMeasurementEntity.class,
                multiContext.getTimeMeasurements().get(1).getId()));
    }

    @Test
    @Rollback(false)
    public void persisting_timemeasurement_should_cascade_persist() {
        System.out.println("persisting_timemeasurement_should_cascade_persist");
        ProcedureEntity procedure = new ProcedureEntity("name", "class", "method");
        MultiContextEntity mce = new MultiContextEntity(new DateTime("2013-06-25T01:16:18.999Z").toDate(),
                new DateTime("2013-06-25T01:16:18.999Z").toDate());
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedure, mce,
                new DateTime("2013-06-25T01:16:18.999Z").toDate(), "duration");

        entityManager.persist(tme);
        assertEquals(tme.getMultiContext(), entityManager.find(MultiContextEntity.class, mce.getId()));
        assertEquals(tme.getProcedure(), entityManager.find(ProcedureEntity.class, procedure.getId()));

    }

}
