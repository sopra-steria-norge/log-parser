package no.osl.cdms.profile.web;

import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import no.osl.cdms.profile.jmx.DataRetrieverMBean;
import no.osl.cdms.profile.persistence.LogRepository;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class DataRetrieverImpl implements DataRetrieverMBean{

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private DataAnalyzer analyzer;

    public DataRetrieverImpl() {

    }

    public List<ProcedureEntity> getAllProcedures(){
        return logRepository.getAllProcedures();
    }

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, int buckets) throws NoSuchElementException {
        List<TimeMeasurement> timeMeasurements = getTimeMeasurements(procedureId, fromDate, toDate, TimeMeasurement.Field.TIMESTAMP);
        if (buckets > 0) {
            timeMeasurements = analyzer.splitIntoBuckets(timeMeasurements, buckets);
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

    public String[] getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages) {
        String[] percentiles = null;
        if (percentiles == null) {
            List<TimeMeasurement> timeMeasurements = logRepository.getTimeMeasurementsByProcedure(
                    fromDate, toDate, logRepository.getProcedure(procedureId), TimeMeasurement.Field.DURATION);
            percentiles = new String[percentages.length];
            for (int i = 0; i < percentages.length; i++) {
                percentiles[i] = new Duration((long) analyzer.percentile(timeMeasurements, percentages[i])).toString();
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

}
