/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import java.util.List;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public interface EntityInsertionCache extends RouteComponent<List<TimeMeasurement>, TimeMeasurement[]>{
    
}
