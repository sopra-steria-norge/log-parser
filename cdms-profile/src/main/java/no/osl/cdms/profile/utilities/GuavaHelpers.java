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
                return input.getKey().endsWith("duration") && !input.getValue().equalsIgnoreCase("null");
            }
        };
    }

    public static Function<Map.Entry<String, String>, TimeMeasurement> getConverter(final Map<String, String> properties) {
        return new Function<Map.Entry<String, String>, TimeMeasurement>() {
            @Override
            public TimeMeasurement apply(Map.Entry<String, String> input) {
                String name, time;
                if (input.getKey().startsWith("LocalThreadContext")) {
                    name = properties.get("LocalThreadContext.id");
                    time = input.getValue();
                } else {
                    name = input.getKey().split("\\.")[2];
                    time = input.getValue();
                }
                return TimeMeasurement.create(name, time);
            }
        };
    }
}
