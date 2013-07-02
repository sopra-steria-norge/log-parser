/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.factories;

import no.osl.cdms.profile.interfaces.TimeMeasurement;
import no.osl.cdms.profile.models.TimeMeasurementImpl;
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
}
