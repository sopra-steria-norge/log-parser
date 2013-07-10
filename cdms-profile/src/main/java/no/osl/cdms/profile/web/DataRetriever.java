package no.osl.cdms.profile.web;

import no.osl.cdms.profile.analyzer.Analyzer;
import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;

    public DataRetriever() {

    }

    public List<TimeMeasurement> getTimeMeasurementAfterDateByProcedure(int procedureId, String jodadatetime) {
        List<TimeMeasurement> timeMeasurements = (List<TimeMeasurement>) getFromCache("getTimeMeasurementAfterDateByProcedure:"
                + procedureId + ":" + jodadatetime);
        if (timeMeasurements == null) {
            Procedure procedure = logRepository.getProcedure(procedureId);
            return logRepository.getTimeMeasurementsByProcedure(new DateTime(jodadatetime).toDate(),
                    new DateTime().toDate(), procedure);
        }
        return timeMeasurements;

    }

    public String[] getPercentileByProcedure(int procedureId, String fromDate, String toDate, int[] percentages) {

        String cacheQuery = "getPercentileByProcedure:" + fromDate + ":" + toDate;
        for(double d : percentages) {
            cacheQuery += ":" + d;
        }
        String[] percentiles = (String[]) getFromCache(cacheQuery);
        if (percentiles == null) {
            Analyzer analyzer = new Analyzer(logRepository.getTimeMeasurementsByProcedure(
                    new DateTime(fromDate).toDate(), new DateTime(toDate).toDate(), logRepository.getProcedure(procedureId)));
            percentiles = new String[percentages.length];
            for (int i = 0; i < percentages.length; i++) {
                percentiles[i] = new Duration((long)analyzer.percentile("total", percentages[i])).toString();
            }
        }
        return percentiles;


    }

    private Object getFromCache(String query) {
        return null;
    }


}
