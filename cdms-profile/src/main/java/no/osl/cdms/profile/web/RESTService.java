package no.osl.cdms.profile.web;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

/**
 * Created with IntelliJ IDEA. User: ohelstro Date: 02.07.13 Time: 11:05 To
 * change this template use File | Settings | File Templates.
 */

@Path("rest")
public class RESTService extends HttpServlet {

    @Autowired
    DataRetriever dataRetriever;

//
//    @GET
//    @Path("multicontext")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<MultiContext> getMultiContextsAfterTimestamp(@QueryParam("timestamp") String timestamp) {
//        List<MultiContext> result = dataRetriever.getMultiContextsAfterTimestamp(timestamp);
//        return result;
//    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("Some rest thing");
    }
}
