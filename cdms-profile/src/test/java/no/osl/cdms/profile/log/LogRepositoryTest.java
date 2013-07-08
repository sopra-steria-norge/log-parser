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

    private ProcedureEntity procedureEntity1, procedureEntity2;
    private TimeMeasurementEntity timeMeasurementEntity1, timeMeasurementEntity2;
    private MultiContextEntity multiContextEntity1;

    @Before
    public void before() {

        procedureEntity1 = new ProcedureEntity("testName1", "testClass1", "testMethod1");
        procedureEntity2 = new ProcedureEntity("testName2", "testClass1", "testMethod2");

        multiContextEntity1 = new MultiContextEntity("testStart", "testEnd");
        timeMeasurementEntity1 = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1, "testTimestamp1", "testDuration1");
        timeMeasurementEntity2 = new TimeMeasurementEntity(procedureEntity2, multiContextEntity1, "testTimestamp2", "testDuration2");
        entityManager.persist(multiContextEntity1);
        entityManager.persist(procedureEntity2);
        entityManager.persist(procedureEntity1);


    }

    @Test
    public void saveMeasured_test() {
        ProcedureEntity me1 = new ProcedureEntity("testName", "testClass1", "testMethod1");
        logRepository.saveProcedure(me1);
        assertEquals(me1, entityManager.find(ProcedureEntity.class, me1.getId()));
    }

    @Test
    public void saveMultiContext_test() {
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1, "timestamp", "duration");
        MultiContextEntity multiContextEntity = new MultiContextEntity("start", "stop");
        multiContextEntity.addTimeMeasurement(tme);
        logRepository.saveMultiContext(multiContextEntity);
        assertEquals(multiContextEntity, entityManager.find(MultiContextEntity.class, multiContextEntity.getId()));
    }

    @Test
    public void saveTimeMeasurement_test() {
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1, "timestamp", "duration");
        logRepository.saveTimeMeasurement(tme);
        assertEquals(tme, entityManager.find(TimeMeasurementEntity.class, tme.getId()));
    }

    @Test
    public void getMeasured_test() {
        assertEquals(logRepository.getProcedure(procedureEntity1.getId()), procedureEntity1);
        assertEquals(logRepository.getProcedure(procedureEntity2.getId()), procedureEntity2);
    }

    @Test
    public void getMultiContext_test() {
        System.out.println(multiContextEntity1.getId());
        assertEquals(logRepository.getMultiContext(multiContextEntity1.getId()), multiContextEntity1);
    }

    @Test
    public void getTimeMeasurement_test() {
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity1.getId()), timeMeasurementEntity1);
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity2.getId()), timeMeasurementEntity2);
    }
}
