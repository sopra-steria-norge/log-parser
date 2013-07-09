package no.osl.cdms.profile.api;

import no.osl.cdms.profile.log.TimeMeasurementEntity;

import java.util.Date;
import java.util.List;


/**
 * User: apalfi
 */
public interface MultiContext {

    int getId();

    void setId(int id);

    Date getStart();

    void setStart(Date start);

    Date getEnd();

    void setEnd(Date end);
}
