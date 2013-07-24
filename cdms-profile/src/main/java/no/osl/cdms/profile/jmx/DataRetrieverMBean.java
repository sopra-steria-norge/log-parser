package no.osl.cdms.profile.jmx;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.ProcedureEntity;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface DataRetrieverMBean {

    public List<ProcedureEntity> getAllProcedures();

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, int buckets) throws NoSuchElementException;

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate) throws NoSuchElementException;

    public List<TimeMeasurement> getTimeMeasurements(int procedureId, DateTime fromDate, DateTime toDate, TimeMeasurement.Field orderBy) throws NoSuchElementException;

    public String[] getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages);

    public Map<Procedure, String[]> getPercentile(DateTime fromDate, DateTime toDate, int[] percentages);
}
