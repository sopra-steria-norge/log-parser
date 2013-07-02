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
    public final String name;
    public final double time;

    public TimeMeasurement(String name, double time) {
        this.name = name;
        this.time = time;
    }

    public static TimeMeasurement create(String name, String time) {
        try {
            DurationConverter converter = ConverterManager.getInstance().getDurationConverter(time);
            return new TimeMeasurement(name, converter.getDurationMillis(time));
        } catch (IllegalArgumentException ile) {
            try {
                return new TimeMeasurement(name, Double.parseDouble(time));
            } catch (NumberFormatException nfe) {
                throw new IllegalArgumentException("Could not unmarshall timestamp \""+time+"\" into TimeMeasurement");
            }
        }
    }

    @Override
    public int compareTo(TimeMeasurement o) {
        return (int) Math.signum(this.time - o.time);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeMeasurement other = (TimeMeasurement) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(this.time) != Double.doubleToLongBits(other.time)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.time) ^ (Double.doubleToLongBits(this.time) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "TimeMeasurement{" + "name=" + name + ", time=" + time + '}';
    }

}
