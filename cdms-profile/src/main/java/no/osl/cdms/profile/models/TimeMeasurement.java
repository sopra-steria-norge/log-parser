/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.models;

import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.DurationConverter;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurement implements Comparable<TimeMeasurement> {

    public enum Type {

        ICWMESSAGE,
        FLIGHTSERVICE,
        WAIT0,
        FLIGHTLEG,
        MILESTONE,
        WAIT1,
        UPDATEMESSAGE
    }
    public final Type name;
    public final double time;

    public TimeMeasurement(Type name, double time) {
        this.name = name;
        this.time = time;
    }

    public static TimeMeasurement create(String name, String time) {
        DurationConverter converter = ConverterManager.getInstance().getDurationConverter(time);
        return new TimeMeasurement(Type.valueOf(name.toUpperCase()), converter.getDurationMillis(time));
    }
    @Override
    public int compareTo(TimeMeasurement o) {
        return (int)Math.signum(this.time-o.time);
    }
}
