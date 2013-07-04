/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.api.Measured;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.MeasuredEntity;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;

/**
 *
 * @author nutgaard
 */
public class EntityFactory {

    public static TimeMeasurement createTimeMeasurement(Measured measured, MultiContext context, String timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((MeasuredEntity) measured, (MultiContextEntity) context, timestamp, duration));
    }

    public static TimeMeasurement createTimeMeasurement(Measured measured, String timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity((MeasuredEntity) measured, null, timestamp, duration));
    }

    public static TimeMeasurement createTimeMeasurement(String timestamp, String duration) {
        return (TimeMeasurement) (new TimeMeasurementEntity(null, null, timestamp, duration));
    }

    public static MultiContext createMultiContext(String start, String end) {
        MultiContext mc = new MultiContextEntity(start, end);
        return mc;
    }

    public static Measured createMeasured(String className, String methodName) {
        Measured m = new MeasuredEntity("", className, methodName);
        return m;
    }

    public static List<TimeMeasurement> createMultiContext(Map<String, String> properties) {
        String start = properties.get("MultiThreadContext.Total.start");
        String end = properties.get("MultiThreadContext.Total.end");
        MultiContextEntity mcme = new MultiContextEntity(start, end);

        Map<String, String> measuredFiltered = Maps.filterEntries(properties, GuavaHelpers.isDuration());
        List<TimeMeasurement> measured = Lists.newLinkedList(Iterables.transform(measuredFiltered.entrySet(), GuavaHelpers.getConverter(properties)));
        for (TimeMeasurement tm : measured) {
            mcme.getTimeMeasurements().add((TimeMeasurementEntity) tm);
            tm.setMultiContext(mcme);
        }
        return measured;
    }

    public static List<TimeMeasurement> createLocalContextTimemeasurement(Map<String, String> properties) {
        String timestamp = properties.get("timestamp");
        String duration = properties.get("LocalThreadContext.duration");
        String[] id = GuavaHelpers.parseKey("LocalThreadContext.id", properties);
        String classname = id[0];
        String methodname = id[1];
        TimeMeasurement tm = createTimeMeasurement(timestamp, duration);
        Measured m = createMeasured(classname, methodname);
        tm.setMeasured((MeasuredEntity) m);
        m.getTimeMeasurements().add((TimeMeasurementEntity) tm);
        List<TimeMeasurement> list = Lists.newLinkedList();
        list.add(tm);
        return list;
    }

    public static List<TimeMeasurement> createTimemeasurement(Map<String, String> properties) {
        if (properties.get("LocalThreadContext.id") != null) {
            return createLocalContextTimemeasurement(properties);
        } else {
            return createMultiContext(properties);
        }
    }
}
