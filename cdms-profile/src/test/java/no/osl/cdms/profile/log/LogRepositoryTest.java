package no.osl.cdms.profile.log;

import org.joda.time.DateTime;
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

        multiContextEntity1 = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.700").toDate());
        timeMeasurementEntity1 = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.017S");
        timeMeasurementEntity2 = new TimeMeasurementEntity(procedureEntity2, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        entityManager.persist(timeMeasurementEntity1);
        entityManager.persist(timeMeasurementEntity2);


    }

    @Test
    public void saveProcedure_test() {
        System.out.println("saveProcedure_test");
        ProcedureEntity procedureEntity = new ProcedureEntity("testName", "testClass1", "testMethod1");
        logRepository.persistNewProcedure(procedureEntity);
        assertEquals(procedureEntity, entityManager.find(ProcedureEntity.class, procedureEntity.getId()));
    }

    @Test
    public void saveMultiContext_test() {
        System.out.println("saveMultiContext_test");
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        MultiContextEntity multiContextEntity = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.458Z").toDate());
        logRepository.persistNewMultiContext(multiContextEntity);
        assertEquals(multiContextEntity, entityManager.find(MultiContextEntity.class, multiContextEntity.getId()));
    }

    @Test
    public void saveTimeMeasurement_test() {
        System.out.println("saveTimeMeasurement_test");
        TimeMeasurementEntity tme = new TimeMeasurementEntity(procedureEntity1, multiContextEntity1,
                new DateTime("2013-06-25T01:15:52.458Z").toDate(), "PT0.107S");
        logRepository.persistNewTimeMeasurement(tme);
        assertEquals(tme, entityManager.find(TimeMeasurementEntity.class, tme.getId()));
    }

    @Test
    public void getProcedure_test() {
        System.out.println("getProcedure_test");
        assertEquals(logRepository.getProcedure(procedureEntity1.getId()), procedureEntity1);
        assertEquals(logRepository.getProcedure(procedureEntity2.getId()), procedureEntity2);
    }

    @Test
    public void getMultiContext_test() {
        System.out.println("getMultiContext_test");
        assertEquals(logRepository.getMultiContext(multiContextEntity1.getId()), multiContextEntity1);
    }

    @Test
    public void getTimeMeasurement_test() {
        System.out.println("getTimemeasurement_test");
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity1.getId()), timeMeasurementEntity1);
        assertEquals(logRepository.getTimeMeasurement(timeMeasurementEntity2.getId()), timeMeasurementEntity2);
    }
}
