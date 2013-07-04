/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.utilities.GuavaHelpers;
import org.springframework.stereotype.Component;

/**
 *
 * @author nutgaard
 */
@Component
public class ObjectMapUnmarshaller {

    public Object unmarshall(Map<String, String> obj) {
        for (String key : obj.keySet()) {
            if (key.startsWith("Multi")) {
                return unmarshallMulti(obj);
            } else {
                return unmarshallLocal(obj);
            }
        }
        throw new InternalError("Somehow there was no key matching Multi or NOT Multi, should not be possible");
    }

    private Object unmarshallLocal(Map<String, String> obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object unmarshallMulti(Map<String, String> obj) {
        MultiContext mc = new MultiContextEntity();
        mc.setStart(obj.get("MultiThreadContext.Total.start"));
        mc.setEnd(obj.get("MultiThreadContext.Total.end"));
        List<TimeMeasurement> list = Lists.newLinkedList();
        Map<String, String> filtered = Maps.filterEntries(obj, GuavaHelpers.isDuration());
//        List<TimeMeasurement> measurements = Lists.transform(filtered.entrySet(), GuavaHelpers.getConverter(obj));
//        mc.setTimeMeasurements(measurements);
        return mc;
    }
}
