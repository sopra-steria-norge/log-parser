/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */

@Component
public interface RouteComponent<S, T> {
    public T process(S s);
}
