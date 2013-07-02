/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.interfaces.TimeMeasurement;
import no.osl.cdms.profile.analyzer.TimeMeasurementImpl;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurementFactory {

    public static TimeMeasurement create(String name, String time) {
        try {
            DurationConverter converter = ConverterManager.getInstance().getDurationConverter(time);
            return new TimeMeasurementImpl(name, converter.getDurationMillis(time));
        } catch (IllegalArgumentException ile) {
            try {
                return new TimeMeasurementImpl(name, Double.parseDouble(time));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Could not unmarshall timestamp \"" + time + "\" into TimeMeasurement");
            }
        }
    }

    public static TimeMeasurement create(String name, double time) {
        return new TimeMeasurementImpl(name, time);
    }

    public static List<TimeMeasurement> create(List<Map<String, String>> data) {
        return create(new LinkedList<TimeMeasurement>(), data);
    }

    public static List<TimeMeasurement> create(List<TimeMeasurement> list, List<Map<String, String>> data) {
        if (list == null) {
            return create(data);
        } else if (data == null) {
            return list;
        } else {
            for (Map<String, String> map : data) {
                Map<String, String> durations = Maps.filterEntries(map, GuavaHelpers.isDuration());
                Iterable<TimeMeasurement> tms = Iterables.transform(durations.entrySet(), GuavaHelpers.getConverter(map));
                list.addAll(Lists.newArrayList(tms));
            }
            return list;
        }
    }
}
