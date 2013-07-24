/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public interface EntityFactory extends RouteComponent<Map<String, String>, List<TimeMeasurement>>{
    public Procedure createProcedure(String className, String methodName);
    public TimeMeasurement createTimeMeasurement(Procedure procedure, Date timestamp, String duration);
    public void setLogRepository(LogRepository logrepository);
}
