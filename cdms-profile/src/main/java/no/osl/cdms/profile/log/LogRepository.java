package no.osl.cdms.profile.log;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LogRepository {

    @PersistenceContext
    private EntityManager entityManager;
}
