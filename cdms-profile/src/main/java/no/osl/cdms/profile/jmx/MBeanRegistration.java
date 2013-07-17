//package no.osl.cdms.profile.jmx;
//
//import no.osl.cdms.profile.web.DataRetriever;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.apache.cxf.management.InstrumentationManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.management.JMException;
//import javax.management.MalformedObjectNameException;
//import javax.management.ObjectName;
//
////@Component
//public class MBeanRegistration {
//
//    @Autowired
//    private SpringBus bus;
//
//    @Autowired
//    private DataRetriever dataRetriever;
//
//    public MBeanRegistration() {
//        InstrumentationManager imanager = bus.getExtension(InstrumentationManager.class);
//
//        final ObjectName oName;// what ever ObjectName works for you
//        try {
//            oName = new ObjectName("no.osl.cdms.profile.web.DataRetriever");
//            imanager.register(dataRetriever, oName);
//        } catch (MalformedObjectNameException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (JMException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//    }
//
//    public void setBus(SpringBus bus) {
//        this.bus = bus;
//    }
//
//    public SpringBus getBus() {
//        return bus;
//    }
//}
