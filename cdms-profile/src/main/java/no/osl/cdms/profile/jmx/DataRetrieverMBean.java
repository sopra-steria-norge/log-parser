package no.osl.cdms.profile.jmx;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.joda.time.DateTime;

public interface DataRetrieverMBean {

    public List<ProcedureEntity> getAllProcedures();

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, int buckets) throws NoSuchElementException;

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate) throws NoSuchElementException;

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, TimeMeasurement.Field orderBy) throws NoSuchElementException;

    public Map<String, Object> getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages);

    public Map<String, Object>[] getPercentile(DateTime fromDate, DateTime toDate, int[] percentages);
}
