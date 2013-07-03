package no.osl.cdms.profile.webservice;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        final Context context = new Context(server, "/graph", Context.REQUEST);
        TestServlet ts = new TestServlet();
        context.addServlet(new ServletHolder(ts), "/*");
        WebAppContext wac = new WebAppContext("src/main/webapp", "/graph");
        wac.setDescriptor("src/main/webapp/WEB-INF/web.xml");

        server.addHandler(wac);
        server.start();
    }


    public static class TestServlet extends HttpServlet{

        public TestServlet()
        {

        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        {

        }
    }
}
