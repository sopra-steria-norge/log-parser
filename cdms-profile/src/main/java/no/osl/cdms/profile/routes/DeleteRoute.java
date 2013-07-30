package no.osl.cdms.profile.routes;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;


public class DeleteRoute extends RouteBuilder {
    private static final Logger logger = Logger.getLogger(DeleteRoute.class);
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void configure() throws Exception {
        DateTime now = DateTime.now();
        DateTime todayAtThree = now.withTime(3, 0, 0, 0);
        DateTime cleanTime;

        if (now.isBefore(todayAtThree)) {
            cleanTime = todayAtThree;
        }
        else {
            cleanTime = todayAtThree.plusDays(1);
        }
        String d = cleanTime.toString("yyyy-MM-dd HH:mm:ss");
        logger.info("Scheduled first DB cleaning for: "+d);
        fromF("timer://DBClean?time="+d+"&period=86400000&fixedRate=true&daemon=true")
                .bean(this, "cleanDB")
                .routeId("DBCLEANINGROUTE");
    }
    @Transactional
    public void cleanDB() {
        logger.info("Cleaning DB of entries older then 14days");
        long start = System.currentTimeMillis();
        Query query = em.createNativeQuery("DELETE FROM SYSTEM.CDM_PROFILE_MULTICONTEXT WHERE MULTICONTEXT_ID = (SELECT MULTICONTEXT_ID FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15); DELETE FROM SYSTEM.CDM_PROFILE_TIMEMEASUREMENT WHERE TIMESTAMP < SYSDATE - 15;");
        query.executeUpdate();
        logger.info("DB cleaning completed in "+(System.currentTimeMillis()-start)+"ms");
    }
       
    @Override
    public String toString() {
        return "DBCLEANINGROUTE";
    }
}
