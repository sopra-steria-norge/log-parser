package no.osl.cdms.profile.utilities;

import no.osl.cdms.profile.jmx.DataRetrieverMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import no.osl.cdms.profile.interfaces.db.Procedure;

/**
 * Created with IntelliJ IDEA.
 * User: apalfi
 * Date: 22.07.13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class JmxClient {

    public static void main(String[] args) throws IOException, MalformedObjectNameException {
        JMXServiceURL url =
            new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-root");



        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        //ObjectName should be same as your MBean name
        ObjectName mbeanName = new ObjectName("no.osl.cdms.profile.web:type=DataRetriever");

        //Get MBean proxy instance that will be used to make calls to registered MBean
        DataRetrieverMBean mbeanProxy =
            (DataRetrieverMBean) MBeanServerInvocationHandler.newProxyInstance(
                    mbeanServerConnection, mbeanName, DataRetrieverMBean.class, true);

        //let's make some calls to mbean through proxy and see the results.
        for (Procedure p : mbeanProxy.getAllProcedures()) {
            System.out.println(p);
        }
        //close the connection
        jmxConnector.close();
    }
}
