package no.osl.cdms.profile.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * Created with IntelliJ IDEA.
 * User: ohelstro
 * Date: 02.07.13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */

/*TODO:
1. Establish connection to db
2. Define queries
3. Write "get"-methods for each graph
    |-> Get info from db
    |-> Create json data with [name: "name" and data[{x: , y:},{x: , y: }]]
*/

@Path("/")
public class GraphWebservice extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Page");
    }
    /**
     *
     * @return
     */
    @GET
    @Path("/data")
    @Produces("application/json")
    public String getData() {
        return "[name:\"Wait\" , [{x:0.5, y:0.3}, {x:0.7, y: 0.5}]]";
    }

//    @Override
    public void handle(String string, HttpServletRequest req, HttpServletResponse resp, int i) throws IOException, ServletException {
        resp.getWriter().write("This is tha webserver");
    }
}
