package no.osl.cdms.profile.utilities;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.Date;
import java.util.Map;
import no.osl.cdms.profile.interfaces.EntityFactory;

import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityFactoryHelpers {

    @Autowired
    private EntityFactory entityFactory;

    public EntityFactoryHelpers() {
    }

    public EntityFactoryHelpers(EntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }
    private static DurationConverter converter = ConverterManager.getInstance().getDurationConverter("PT0.012S");
    private static DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");

    public Predicate<Map.Entry<String, String>> isDuration() {
        return new Predicate<Map.Entry<String, String>>() {
            @Override
            public boolean apply(Map.Entry<String, String> input) {
                if (input == null || input.getKey() == null || input.getValue() == null) {
                    return false;
                }
                return input.getKey().endsWith("duration") && !input.getValue().equalsIgnoreCase("null");
            }
        };
    }

    public Function<Map.Entry<String, String>, TimeMeasurement> getTimemeasurementConverter(final Map<String, String> properties) {
        return new Function<Map.Entry<String, String>, TimeMeasurement>() {
            @Override
            public TimeMeasurement apply(Map.Entry<String, String> input) {
                String[] measured = parseKey(input.getKey(), properties);
                String time = parseDuration(input.getValue());

                Procedure m = (ProcedureEntity) entityFactory.createProcedure(measured[0], measured[1]);
                TimeMeasurement tm = entityFactory.createTimeMeasurement(m, parseDateString(properties.get("timestamp")), time);

                return tm;
            }
        };
    }
    public Date parseDateString(String string) {
        return dtf.parseDateTime(string).toDate();
    }

    public String[] parseKey(String key, Map<String, String> map) {
        if (key == null) {
            throw new IllegalArgumentException("Key for timemeasurement cannot be <null>");//Should not be possible, cannot store null as key in maps
        }
        if (key.startsWith("LocalThread")) {
            String id = map.get("LocalThreadContext.id");
            if (id == null) {
                throw new NullPointerException("LocalThreadContext.id was not found within this object");
            }
            String[] out = id.split("\\.");
            return out;
        } else {
            String[] parts = key.split("[\\.:]");
            if (parts.length < 3) {
                throw new NullPointerException("Unmarshalling of map to timemeasure failed, too few key parts: " + key);
            }
            if (parts[1].equals("Total") || parts[1].equals("Wait")) {
                return new String[]{parts[1], ""};
            } else {
                return new String[]{parts[2], parts[3]};
            }
        }
    }

    public String parseDuration(String duration) {
        try {
            long ans = converter.getDurationMillis(duration);
            return duration;
        } catch (Exception ex) {
            try {
                double d = Double.parseDouble(duration);
                return "PT" + (d / 1000) + "S";
            } catch (Exception e) {
                throw new IllegalArgumentException("Duration " + duration + " was not recognized");
            }
        }
    }
}