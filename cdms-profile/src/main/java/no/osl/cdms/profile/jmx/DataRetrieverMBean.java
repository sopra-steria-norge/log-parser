package no.osl.cdms.profile.jmx;

import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LayoutEntity;
import no.osl.cdms.profile.log.ProcedureEntity;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

public interface DataRetrieverMBean {

    public List<ProcedureEntity> getAllProcedures();

    public List<TimeMeasurement> getTimeMeasurementBetweenDatesByProcedure(int procedureId, DateTime fromDate,
                                                                           DateTime toDate);

    public String[] getPercentileByProcedure(int procedureId, DateTime fromDate, DateTime toDate, int[] percentages);

    public Map<Procedure, String[]> getPercentile(DateTime fromDate, DateTime toDate, int[] percentages);

    public List<LayoutEntity> getAllLayoutEntities();

    public List<String> getAllLayoutEntityNames();

    public LayoutEntity getLayoutEntity(String name);
}
