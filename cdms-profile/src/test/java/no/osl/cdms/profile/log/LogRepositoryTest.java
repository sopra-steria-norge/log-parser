package no.osl.cdms.profile.log;

import org.junit.Before;
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

    private MeasuredEntity measuredEntity1, measuredEntity2;
    private TimeMeasurementEntity timeMeasurementEntity1, timeMEasurementEntity2;
    private MultiContextEntity multiContextEntity1;

    @Before
    public void before() {

        measuredEntity1 = new MeasuredEntity("testName1", "testClass1", "testMethod1");
        measuredEntity2 = new MeasuredEntity("testName2", "testClass2", "testmethod2");

        multiContextEntity1 = new MultiContextEntity("testStart", "testEnd");

        timeMeasurementEntity1 = new TimeMeasurementEntity(measuredEntity1, multiContextEntity1, "testTimestamp1", "testDuration1");
        timeMEasurementEntity2 = new TimeMeasurementEntity(measuredEntity2, multiContextEntity1, "testTimestamp2", "testDuration2");

        measuredEntity1.getTimeMeasurements().add(timeMeasurementEntity1);
        measuredEntity2.getTimeMeasurements().add(timeMEasurementEntity2);
        multiContextEntity1.getTimeMeasurements().add(timeMeasurementEntity1);
        multiContextEntity1.getTimeMeasurements().add(timeMEasurementEntity2);

        entityManager.persist(measuredEntity1);
        entityManager.persist(measuredEntity2);
        entityManager.persist(multiContextEntity1);

    }

    @Test
    public void saveMeasured_test() {
        MeasuredEntity me1 = new MeasuredEntity("testName", "testClass1", "testMethod1");
        logRepository.saveMeasured(me1);
        assertEquals(me1, entityManager.find(MeasuredEntity.class, me1.getId()));
    }

    @Test
    public void saveMultiContext_test() {
        TimeMeasurementEntity tme = new TimeMeasurementEntity(measuredEntity1, multiContextEntity1, "timestamp", "duration");
        MultiContextEntity multiContextEntity = new MultiContextEntity("start", "stop");
        multiContextEntity.getTimeMeasurements().add(tme);
        logRepository.saveMultiContext(multiContextEntity);
        assertEquals(multiContextEntity, entityManager.find(MultiContextEntity.class, multiContextEntity.getId()));
    }

    @Test
    public void saveTimeMeasurement_test() {
        TimeMeasurementEntity tme = new TimeMeasurementEntity(measuredEntity1, multiContextEntity1, "timestamp", "duration");
        logRepository.saveTimeMeasurement(tme);
        assertEquals(tme, entityManager.find(TimeMeasurementEntity.class, tme.getId()));
    }

    @Test
    public void getMeasured_test() {
        assertEquals(logRepository.getMeasured(measuredEntity1.getId()), measuredEntity1);
        assertEquals(logRepository.getMeasured(measuredEntity2.getId()), measuredEntity2);
    }

    @Test
    public void getMultiContext_test() {
        System.out.println(multiContextEntity1.getId());
        assertEquals(logRepository.getMultiContext(multiContextEntity1.getId()), multiContextEntity1);
    }

    @Test
    public void getTimeMeasurement_test() {
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity1.getId()), timeMeasurementEntity1);
        assertEquals(logRepository.getTimeMeasurement(timeMEasurementEntity2.getId()), timeMEasurementEntity2);
    }

}
