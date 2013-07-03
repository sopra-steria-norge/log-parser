/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public interface Parser {
    public Map<String, String> parse(Map<String, String> properties, String obj);
    public Map<String, String> parse(String obj);
}

