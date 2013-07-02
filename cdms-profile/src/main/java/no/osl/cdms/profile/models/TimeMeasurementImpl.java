/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.models;

import no.osl.cdms.profile.interfaces.TimeMeasurement;

/**
 *
 * @author nutgaard
 */
public class TimeMeasurementImpl implements TimeMeasurement {
    public final String name;
    public final double time;

    public TimeMeasurementImpl(String name, double time) {
        this.name = name;
        this.time = time;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public double getTime() {
        return time;
    }    

    @Override
    public int compareTo(TimeMeasurement o) {
        return (int) Math.signum(this.getTime() - o.getTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeMeasurementImpl other = (TimeMeasurementImpl) obj;
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
