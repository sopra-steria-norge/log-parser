package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.TimeMeasurementEntity;

import java.util.List;

/**
 * User: apalfi
 */
public interface Measured {

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    String getClassName();

    void setClassName(String className);

    String getMethod();

    void setMethod(String method);

    List<TimeMeasurementEntity> getTimeMeasurements();

    void setTimeMeasurements(List<TimeMeasurementEntity> timeMeasurements);

    public boolean equals(Object obj);

    @Override
    public int hashCode();

    void addTimeMeasurement(TimeMeasurement timeMeasurement);
}
