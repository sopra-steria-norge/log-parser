package no.osl.cdms.profile.web;

import no.osl.cdms.profile.analyzer.Analyzer;
import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LayoutEntity;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.log.ProcedureEntity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;

    public DataRetriever() {

    }

    public List<ProcedureEntity> getAllProcedures(){
        List<ProcedureEntity> procedures = (List<ProcedureEntity>) getFromCache("getAllProcedures");
        if (procedures == null) {
            procedures = logRepository.getAllProcedures();
        }
        return procedures;
    }

    public List<TimeMeasurement> getTimeMeasurementBetweenDatesByProcedure(int procedureId, String jodadatetimeFrom,
                                                               String jodadatetimeTo) throws IllegalArgumentException{
        if (jodadatetimeTo == null) {
            jodadatetimeTo = new DateTime().toString();
        }

        List<TimeMeasurement> timeMeasurements = (List<TimeMeasurement>)
                getFromCache("getTimeMeasurementBetweenDatesByProcedure:"+ procedureId + ":" + jodadatetimeFrom + ":" +
                jodadatetimeTo);
        if (timeMeasurements == null) {
            Procedure procedure = logRepository.getProcedure(procedureId);
            return logRepository.getTimeMeasurementsByProcedure(new DateTime(jodadatetimeFrom).toDate(),
                    new DateTime(jodadatetimeTo).toDate(), procedure);
        }
        return timeMeasurements;

    }

    public String[] getPercentileByProcedure(int procedureId, String fromDate, String toDate, int[] percentages)
                                                                             throws IllegalArgumentException {
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

    public List<LayoutEntity> getAllLayoutEntities() {
         return logRepository.getAllLayoutEntities();
    }

    public Map<String, Integer> getAllLayoutEntityNames() {
        return logRepository.getAllLayoutEntityNames();
    }

    public LayoutEntity getLayoutEntity(int id) {
        return logRepository.getLayoutEntity(id);
    }

    private Object getFromCache(String query) {
        return null;
    }


}
