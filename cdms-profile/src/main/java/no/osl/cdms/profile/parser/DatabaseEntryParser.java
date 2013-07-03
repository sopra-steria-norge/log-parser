package no.osl.cdms.profile.parser;

import java.util.Map;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.springframework.stereotype.Component;

@Component
public class DatabaseEntryParser {

    public TimeMeasurementEntity parse(Map<String, String> logEntry) {
        return new TimeMeasurementEntity();
    }
}
