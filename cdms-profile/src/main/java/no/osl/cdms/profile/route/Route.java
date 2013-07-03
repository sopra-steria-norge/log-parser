/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.route;

import java.util.Map;
import no.osl.cdms.profile.interfaces.DataAnalyzer;
import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nutgaard
 */
//@Component
public class Route {// implements Processor {            
    @Autowired
    private Parser parser;
    
    @Autowired
    private DataAnalyzer analyzer;
//    
//    @Autowired
//    private DatabaseEntryParser db;
//
    public void process(Exchange exchange) throws Exception {
        String line = exchange.getIn().getBody(String.class);
        Map<String, String> parsed = parser.parse(line);
//        TimeMeasurementEntity tme = db.parse(parsed);
    }
}
