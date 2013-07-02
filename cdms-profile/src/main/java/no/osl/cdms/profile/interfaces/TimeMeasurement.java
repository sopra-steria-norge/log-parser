/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

/**
 *
 * @author nutgaard
 */
public interface TimeMeasurement extends Comparable<TimeMeasurement> {
    public String getName();
    public double getTime();
    @Override
    public int compareTo(TimeMeasurement o);
    @Override
    public boolean equals(Object obj);
    @Override
    public int hashCode();
    @Override
    public String toString();
}
