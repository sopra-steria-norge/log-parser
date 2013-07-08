package no.osl.cdms.profile.factories;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
public class EntityFactoryTest {

    private EntityFactory entityFactory = EntityFactory.getInstance();

    @Autowired
    private LogRepository logRepository;


    @Before
    public void before() {
        entityFactory.setLogRepository(logRepository);
    }

    @Test
    @Rollback(false)
    public void uniqueProcedure() {
        ProcedureEntity old = new ProcedureEntity("name", "class", "method");
        logRepository.saveProcedure(old);
        Procedure current = entityFactory.createProcedure("name", "class", "method");

        assertEquals(old, current);
    }
}
