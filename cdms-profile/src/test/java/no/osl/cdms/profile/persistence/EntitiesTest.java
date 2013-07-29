package no.osl.cdms.profile.persistence;

import no.osl.cdms.profile.persistence.TimeMeasurementEntity;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import no.osl.cdms.profile.persistence.MultiContextEntity;
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
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class EntitiesTest {

    @PersistenceContext
    private EntityManager entityManager;

    private ProcedureEntity procedureEntity;
    private MultiContextEntity multiContextEntity;
    private TimeMeasurementEntity timeMeasurementEntity;

    @Before
    public void before() {
        procedureEntity = new ProcedureEntity("name", "classname", "method");
        multiContextEntity = new MultiContextEntity(new DateTime("2013-06-25T01:16:18.847Z").toDate(),
                new DateTime("2013-06-25T01:16:18.900Z").toDate());
        timeMeasurementEntity = new TimeMeasurementEntity(procedureEntity, multiContextEntity,
                new DateTime("2013-06-25T01:16:18.847Z").toDate(), "PT0.017S");

        entityManager.persist(timeMeasurementEntity);
    }

    @Test
    public void persisting_timemeasurement_should_cascade_persist() {
        System.out.println("persisting_timemeasurement_should_cascade_persist");

        assertEquals(timeMeasurementEntity.getMultiContext(), entityManager.find(MultiContextEntity.class,
                multiContextEntity.getId()));
        assertEquals(timeMeasurementEntity.getProcedure(), entityManager.find(ProcedureEntity.class,
                procedureEntity.getId()));

    }

}
