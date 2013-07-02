package no.osl.cdms.profile.webservice;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Created with IntelliJ IDEA.
 * User: ohelstro
 * Date: 02.07.13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public class FakeWebServer {

    private static int PORT = 9000;

    public static void main(String[] args) throws Exception{
        final Server server = new Server(PORT);
        final Context context = new Context(server, "/", Context.REQUEST);

        WebAppContext wac = new WebAppContext("src/main/webapp", "/index.html");
        wac.setDescriptor("src/main/webapp/WEB-INF/web.xml");

        server.addHandler(wac);
        server.start();
    }
}
