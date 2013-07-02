/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.analyzer;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.interfaces.TimeMeasurement;
import no.osl.cdms.profile.utilities.GuavaHelpers;

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
                Map<String, String> durations = Maps.filterEntries(map, GuavaHelpers.isDuration());
                Iterable<TimeMeasurement> tms = Iterables.transform(durations.entrySet(), GuavaHelpers.getConverter(map));
                list.addAll(Lists.newArrayList(tms));
            }
            return list;
        }
    }
}
