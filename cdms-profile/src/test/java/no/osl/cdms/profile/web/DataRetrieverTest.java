package no.osl.cdms.profile.web;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:META-INF/spring/cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class DataRetrieverTest {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private DataRetriever dataRetriever;

    @Test
    public void getTimeMeasurementAfterDateByProcedure_test() {
        System.out.println("getTimeMeasurementAfterDateByProcedure_test");
        ProcedureEntity procedure = new ProcedureEntity("name", "classCname", "method");
        TimeMeasurementEntity timeMeasurement = new TimeMeasurementEntity(procedure, null, new DateTime("2012-06-25T01:15:52.458Z")
                .toDate(), "PT0.107S");
        TimeMeasurementEntity timeMeasurement2 = new TimeMeasurementEntity(procedure, null, new DateTime("2012-06-25T01:15:52.458Z")
                .toDate(), "PT0.107S");
        TimeMeasurementEntity timeMeasurement3 = new TimeMeasurementEntity(procedure, null, new DateTime("2010-06-25T01:15:52.458Z")
                .toDate(), "PT0.107S");

        logRepository.persistNewTimeMeasurement(timeMeasurement);
        logRepository.persistNewTimeMeasurement(timeMeasurement2);
        logRepository.persistNewTimeMeasurement(timeMeasurement3);

        List<TimeMeasurement> expected = new ArrayList<TimeMeasurement>();
        expected.add(timeMeasurement);
        expected.add(timeMeasurement2);

        List<TimeMeasurement> tm = dataRetriever.getTimeMeasurementAfterDateByProcedure(procedure.getId(), "2011-06-25T01:15:52.458Z");
        assertEquals(expected, tm);

    }

}
