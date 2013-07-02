/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.utilities;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Map;
import no.osl.cdms.profile.models.TimeMeasurement;

/**
 *
 * @author nutgaard
 */
public class GuavaHelpers {

    public static Predicate<Map.Entry<String, String>> isDuration() {
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

    public static Function<Map.Entry<String, String>, TimeMeasurement> getConverter(final Map<String, String> properties) {
        return new Function<Map.Entry<String, String>, TimeMeasurement>() {
            @Override
            public TimeMeasurement apply(Map.Entry<String, String> input) {
                String name = parseKey(input.getKey(), properties);
                String time = input.getValue();
                return TimeMeasurement.create(name, time);
            }
        };
    }

    private static String parseKey(String key, Map<String, String> map) {
        if (key == null){
            throw new IllegalArgumentException("Key for timemeasurement cannot be <null>");//Should not be possible, cannot store null as key in maps
        }
        if (key.startsWith("LocalThread")) {
            String id = map.get("LocalThreadContext.id");
            if (id == null) {
                throw new NullPointerException("LocalThreadContext.id was not found within this object");
            }
            return id;
        } else {
            String[] parts = key.split("[\\.:]");
            if (parts.length < 3){
                throw new NullPointerException("Unmarshalling of map to timemeasure failed, too few key parts: "+key);
            }
            if (parts[1].equals("Total") || parts[1].equals("Wait")) {
                return parts[1];
            } else {
                return parts[2] + "." + parts[3];
            }
        }
    }
}
