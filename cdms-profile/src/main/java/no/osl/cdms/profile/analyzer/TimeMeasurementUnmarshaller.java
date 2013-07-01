/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.models.TimeMeasurement;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurementUnmarshaller {

    public TimeMeasurementUnmarshaller() {
    }

    public List<TimeMeasurement> unmarshall(List<Map<String, String>> data) {
        return unmarshall(new LinkedList<TimeMeasurement>(), data);
    }

    public List<TimeMeasurement> unmarshall(List<TimeMeasurement> list, List<Map<String, String>> data) {
        if (list == null) {
            return unmarshall(data);
        } else if (data == null) {
            return list;
        } else {
            for (Map<String, String> map : data) {
                Map<String, String> durations = Maps.filterEntries(map, isDuration());
                Iterable<TimeMeasurement> tms = Iterables.transform(durations.entrySet(), getConverter(map));
                list.addAll(Lists.newArrayList(tms));
            }
            return list;
        }
    }

    private Predicate<Map.Entry<String, String>> isDuration() {
        return new Predicate<Map.Entry<String, String>>() {
            @Override
            public boolean apply(Map.Entry<String, String> input) {
                return input.getKey().endsWith("duration") && !input.getValue().equalsIgnoreCase("null");
            }
        };
    }

    private Function<Map.Entry<String, String>, TimeMeasurement> getConverter(final Map<String, String> properties) {
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
