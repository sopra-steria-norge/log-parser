package no.osl.cdms.profile.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class LogRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LogRepository logRepository;

    //Test test
    @Test
    public void simple_entityManagerTest() {
        MeasuredEntity measured = new MeasuredEntity("testName", "testClass", "testMethod", new ArrayList<TimeMeasurementEntity>());
        entityManager.persist(measured);
        assertEquals(measured, entityManager.find(MeasuredEntity.class, measured.getId()));
        TimeMeasurementEntity tm = new TimeMeasurementEntity(measured, null, "timestamp", "duration");
        measured.getTimeMeasurements().add(tm);
        entityManager.persist(tm);
        entityManager.flush();

        MeasuredEntity measured2 = entityManager.find(MeasuredEntity.class, measured.getId());
        TimeMeasurementEntity tm2 = entityManager.find(TimeMeasurementEntity.class, tm.getId());

        assertNotNull(measured2);
        assertNotNull(tm2);
        assertTrue(measured.getTimeMeasurements().size() == 1);
        assertEquals(tm2, measured.getTimeMeasurements().get(0));
        assertEquals(tm2.getMeasured(), measured);
    }



}
