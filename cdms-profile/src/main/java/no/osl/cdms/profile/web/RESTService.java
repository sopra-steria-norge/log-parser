package no.osl.cdms.profile.web;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

/**
 * Created with IntelliJ IDEA. User: ohelstro Date: 02.07.13 Time: 11:05 To
 * change this template use File | Settings | File Templates.
 */

/*TODO:
 1. Establish connection to db
 2. Define queries
 3. Write "get"-methods for each graph
 |-> Get info from db
 |-> Create json data with [name: "name" and data[{x: , y:},{x: , y: }]]
 */
@Path("/rest")
public class RESTService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("[{timestamp: 1, duration: 12}]");
    }
}
