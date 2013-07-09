package no.osl.cdms.profile.web;

import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.Procedure;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRetriever {

    @Autowired
    private LogRepository logRepository;

    public DataRetriever() {

    }

    public List<TimeMeasurement> getTimeMeasurementAfterDateByProcedure(int procedureId, String date) {
        if (!isQueryInCache("getTimeMeasurementAfterDateByProcedure:" + procedureId + ":" + date)) {
            Procedure procedure = logRepository.getProcedure(procedureId);
            return logRepository.getTimeMeasurementsAfterDateByProcedure(new DateTime(date).toDate(), procedure);
        }
        return (List<TimeMeasurement>) getFromCache("getTimeMeasurementAfterDateByProcedure:" + procedureId + ":" + date);

    }

    private boolean isQueryInCache(String query) {
        return false;
    }

    private Object getFromCache(String query) {
        return null;
    }


}
