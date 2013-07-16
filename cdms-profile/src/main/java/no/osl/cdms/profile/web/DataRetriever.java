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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;

    public DataRetriever() {

    }

    public List<ProcedureEntity> getAllProcedures(){
        return logRepository.getAllProcedures();
    }

    public List<TimeMeasurement> getTimeMeasurementBetweenDatesByProcedure(int procedureId, DateTime fromDate,
                                                               DateTime toDate){
        if (toDate == null) {
            toDate = new DateTime();
        }
        Procedure procedure = logRepository.getProcedure(procedureId);
        return logRepository.getTimeMeasurementsByProcedure(fromDate, toDate, procedure);
    }

    public String[] getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages) {
        /*String cacheQuery = "getPercentileByProcedure:" + fromDate + ":" + toDate;
        for(double d : percentages) {
            cacheQuery += ":" + d;
        }*/
        String[] percentiles = null;//(String[]) getFromCache(cacheQuery);
        if (percentiles == null) {
            Analyzer analyzer = new Analyzer(logRepository.getTimeMeasurementsByProcedure(
                    new DateTime(fromDate), new DateTime(toDate), logRepository.getProcedure(procedureId)));
            percentiles = new String[percentages.length];
            for (int i = 0; i < percentages.length; i++) {
                percentiles[i] = new Duration((long)analyzer.percentile("total", percentages[i])).toString();
            }
        }

        return percentiles;
    }

    public Map<Procedure, String[]> getPercentile(DateTime fromDate, DateTime toDate, int[] percentages) {
        Map<Procedure, String[]> percentileMap = new HashMap<Procedure, String[]>();
        List<ProcedureEntity> procedures = logRepository.getAllProcedures();

        String[] percentiles;
        for (Procedure procedure: procedures) {
            percentiles = getPercentileByProcedure(procedure.getId(), fromDate, toDate, percentages);
            percentileMap.put(procedure, percentiles);
        }
        return percentileMap;
    }

    public List<LayoutEntity> getAllLayoutEntities() {
         return logRepository.getAllLayoutEntities();
    }

    public List<String> getAllLayoutEntityNames() {
        return logRepository.getAllLayoutEntityNames();
    }

    public LayoutEntity getLayoutEntity(String name) {
        return logRepository.getLayoutEntity(name);
    }

}
