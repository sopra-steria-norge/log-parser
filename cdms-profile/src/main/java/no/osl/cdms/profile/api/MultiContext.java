package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.TimeMeasurementEntity;

import java.util.List;

/**
 * User: apalfi
 */
public interface MultiContext {

    int getId();

    void setId(int id);

    String getStart();

    void setStart(String start);

    String getEnd();

    void setEnd(String end);

    List<TimeMeasurementEntity> getTimeMeasurements();

    void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements);

    void addTimeMeasurement(TimeMeasurement timeMeasurement);
}
