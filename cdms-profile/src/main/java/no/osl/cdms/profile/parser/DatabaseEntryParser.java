package no.osl.cdms.profile.parser;

import java.util.Map;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

@Component
public class DatabaseEntryParser {

    @Handler
    public TimeMeasurementEntity parse(Map<String, String> logEntry) {
        return new TimeMeasurementEntity();
    }
}
