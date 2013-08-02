/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.utilities;

import java.io.IOException;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import no.osl.cdms.profile.services.jmx.JMXService;
import org.junit.Test;
import sun.management.ConnectorAddressLink;

/**
 *
 * @author nutgaard
 */
public class JMXTest {

    @Test
    public void jmxConnect() throws Exception {
        MBeanServerConnection mbsc = getLocalMBeanServerConnectionStatic(6616);
        System.out.println("Domains: ");
        for (String s : mbsc.getDomains()) {
            System.out.println("    "+s);
        }
        ObjectName name = ObjectName.getInstance("no.osl.cdms.profile.services.jmx:name=jmxServiceBean,type=JMXService");
        
        long icw = JMX.newMBeanProxy(mbsc, name,  JMXService.class).getICW();
        System.out.println("ICW: "+icw);
        
        
    }
    public static MBeanServerConnection getLocalMBeanServerConnectionStatic(int pid) {
    try {
        String address = ConnectorAddressLink.importFrom(pid);
        JMXServiceURL jmxUrl = new JMXServiceURL(address);
        return JMXConnectorFactory.connect(jmxUrl).getMBeanServerConnection();
    } catch (IOException e) {
        throw new RuntimeException("Of course you still have to implement a good connection handling");
    }
}
}
