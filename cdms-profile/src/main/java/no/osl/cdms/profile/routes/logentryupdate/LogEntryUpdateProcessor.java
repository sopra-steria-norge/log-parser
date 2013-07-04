package no.osl.cdms.profile.routes.logentryupdate;

import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

/**
 * Class to handle the insertion of log entries to the database
 */
@Component
public class LogEntryUpdateProcessor {

    @Handler
    public void startProcess(TimeMeasurementEntity timeMeasurementEntity) {

    }
}
