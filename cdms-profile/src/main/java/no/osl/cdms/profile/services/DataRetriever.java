package no.osl.cdms.profile.services;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.persistence.LogRepository;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;
    @Autowired
    private DataAnalyzer analyzer;

    public DataRetriever() {
    }
    
    public List<ProcedureEntity> getAllProcedures() {
        return logRepository.getAllProcedures();
    }

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, int buckets) throws NoSuchElementException {
        List<TimeMeasurement> timeMeasurements = getTimeMeasurements(procedureId, fromDate, toDate, TimeMeasurement.Field.TIMESTAMP);
        if (buckets > 0) {
            timeMeasurements = analyzer.splitIntoBuckets(timeMeasurements, fromDate, toDate, buckets);
        }
        return timeMeasurements;
    }

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate) throws NoSuchElementException {
        return getTimeMeasurements(procedureId, fromDate, toDate, null);
    }

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, TimeMeasurement.Field orderBy) throws NoSuchElementException {
        if (toDate == null) {
            toDate = new DateTime();
        }
        Procedure procedure = logRepository.getProcedure(procedureId);
        if (procedure == null) {
            throw new NoSuchElementException();
        }
        return logRepository.getTimeMeasurementsByProcedure(fromDate, toDate, procedure, orderBy);
    }

    public Map<String, Object> getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages) {
        Map<String, Object> percentilesMap = Maps.newHashMap();

        List<TimeMeasurement> timeMeasurements = logRepository.getTimeMeasurementsByProcedure(
                fromDate, toDate, logRepository.getProcedure(procedureId), TimeMeasurement.Field.DURATION);
        Map<Integer, String> percentiles = Maps.newHashMap();
        
        for (int i = 0; i < percentages.length; i++) {
            String percentile = new Duration((long) analyzer.percentile(timeMeasurements, percentages[i])).toString();
            percentiles.put(percentages[i], percentile);
        }
        percentilesMap.put("id", procedureId);
        percentilesMap.put("percentiles", percentiles);
        return percentilesMap;
    }

    public Map<String, Object>[] getPercentile(DateTime fromDate, DateTime toDate, int[] percentages) {
        List<ProcedureEntity> procedures = logRepository.getAllProcedures();
        Map<String, Object>[] percentilesMap = new HashMap[procedures.size()];
        
        int i = 0;
        for (Procedure procedure : procedures) {
            percentilesMap[i++] = getPercentileByProcedure(procedure.getId(), fromDate, toDate, percentages);
        }
        return percentilesMap;
    }
}
