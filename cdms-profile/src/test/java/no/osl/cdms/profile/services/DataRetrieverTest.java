package no.osl.cdms.profile.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.persistence.LogRepository;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import no.osl.cdms.profile.persistence.TimeMeasurementEntity;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:test-cdms-profile-ctx.xml",
    "classpath:test-cdms-profile-infra-ctx.xml"})
@Transactional
public class DataRetrieverTest {

    private static final Logger logger = Logger.getLogger(DataRetrieverTest.class);
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
        timeMeasurement2 = new TimeMeasurementEntity(procedure, null, new DateTime("2012-06-25T01:15:52.459Z")
                .toDate(), "PT0.207S");
        timeMeasurement3 = new TimeMeasurementEntity(procedure, null, new DateTime("2010-06-25T01:15:52.458Z")
                .toDate(), "PT0.307S");

        logRepository.persistNewTimeMeasurement(timeMeasurement);
        logRepository.persistNewTimeMeasurement(timeMeasurement2);
        logRepository.persistNewTimeMeasurement(timeMeasurement3);
    }

    @Test
    public void getTimeMeasurementBetweenDatesByProcedure_test() {
        logger.info("getTimeMeasurementAfterDateByProcedure_test");


        List<TimeMeasurement> expected = new ArrayList<TimeMeasurement>();
        expected.add(timeMeasurement2);
        expected.add(timeMeasurement);

        List<TimeMeasurement> tm = dataRetriever.getTimeMeasurements(procedure.getId(),
                new DateTime("2011-06-25T01:15:52.458Z"), new DateTime("2013-06-25T01:15:52.460Z"));
        logger.info(Arrays.toString(expected.toArray()));
        logger.info(tm);
        assertEquals(expected, tm);

    }

    @Test
    public void getPercentileByProcedure_test() {
        logger.info("getPercentileByProcedure_test");
        int[] percentages = {0, 50, 87, 100};

        Map<String, Object> percentilesMap = dataRetriever.getPercentileByProcedure(procedure.getId(), new DateTime("2002-06-25T01:15:52.458Z"),
                new DateTime(), percentages);
        Map<Integer, String> percentiles = (Map) percentilesMap.get("percentiles");
        String[] expected = {new Duration(107).toString(), new Duration(207).toString(),
            new Duration(307).toString(), new Duration(307).toString()};
        for (int i = 0; i < percentages.length; i++) {
            assertTrue(expected[i].equals(percentiles.get(percentages[i])));
        }


    }
}
