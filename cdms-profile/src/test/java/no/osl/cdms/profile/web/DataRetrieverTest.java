package no.osl.cdms.profile.web;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
        "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class DataRetrieverTest {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private DataRetriever dataRetriever;

    private TimeMeasurementEntity timeMeasurement, timeMeasurement2, timeMeasurement3;
    private ProcedureEntity procedure;

    @Before
    public void before() {
        procedure = new ProcedureEntity("name", "classCname", "method");
        timeMeasurement = new TimeMeasurementEntity(procedure, null, new DateTime("2012-06-25T01:15:52.458Z")
                .toDate(), "PT0.107S");
        timeMeasurement2 = new TimeMeasurementEntity(procedure, null, new DateTime("2012-06-25T01:15:52.458Z")
                .toDate(), "PT0.207S");
        timeMeasurement3 = new TimeMeasurementEntity(procedure, null, new DateTime("2010-06-25T01:15:52.458Z")
                .toDate(), "PT0.307S");

        logRepository.persistNewTimeMeasurement(timeMeasurement);
        logRepository.persistNewTimeMeasurement(timeMeasurement2);
        logRepository.persistNewTimeMeasurement(timeMeasurement3);
    }

    @Test
    public void getTimeMeasurementBetweenDatesByProcedure_test() {
        System.out.println("getTimeMeasurementAfterDateByProcedure_test");


        List<TimeMeasurement> expected = new ArrayList<TimeMeasurement>();
        expected.add(timeMeasurement);
        expected.add(timeMeasurement2);

        List<TimeMeasurement> tm = dataRetriever.getTimeMeasurementBetweenDatesByProcedure(procedure.getId(),
                new DateTime("2011-06-25T01:15:52.458Z"), new DateTime("2013-06-25T01:15:52.458Z"));
        assertEquals(expected, tm);

    }

    @Test
    public void getPercentileByProcedure_test() {
        System.out.println("getPercentileByProcedure_test");
        int[] percentages = {0, 50, 87, 100};

        String[] percentiles = dataRetriever.getPercentileByProcedure(procedure.getId(), new DateTime("2002-06-25T01:15:52.458Z"),
                new DateTime(), percentages);
        String[] expected = {new Duration(107).toString(), new Duration(207).toString(),
                new Duration(307).toString(), new Duration(307).toString()};
        for(int i = 0; i < percentages.length; i++) {
            assertTrue(expected[i].equals(percentiles[i]));
        }


    }

}
