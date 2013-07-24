/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

/**
 *
 * @author nutgaard
 */
public interface RouteComponent<S, T> {
    public T process(S s);
}
