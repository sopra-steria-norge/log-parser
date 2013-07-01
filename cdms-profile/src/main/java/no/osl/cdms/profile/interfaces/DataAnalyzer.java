/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

/**
 *
 * @author nutgaard
 */
public interface DataAnalyzer {
    public double average(String id);
    public double stddev(String id);
    public double percentile(String id, int percent);
    public double[] buckets(String id, int NOFBuckets);
}
