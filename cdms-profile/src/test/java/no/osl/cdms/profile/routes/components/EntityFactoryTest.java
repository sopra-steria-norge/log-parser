package no.osl.cdms.profile.routes.components;

import no.osl.cdms.profile.interfaces.EntityFactory;
import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.persistence.LogRepository;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
public class EntityFactoryTest {

    @Autowired
    private EntityFactory entityFactory;

    @Autowired
    private LogRepository logRepository;


    @Before
    public void before() {
        entityFactory.setLogRepository(logRepository);
    }

    @Test
    public void uniqueProcedure() {

        ProcedureEntity old = new ProcedureEntity("name", "class", "method");
        logRepository.persistNewProcedure(old);
        Procedure current = entityFactory.createProcedure("class", "method");

        assertEquals(old, current);
    }
}
