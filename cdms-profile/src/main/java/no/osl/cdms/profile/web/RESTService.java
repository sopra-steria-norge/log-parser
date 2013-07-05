package no.osl.cdms.profile.web;

import no.osl.cdms.profile.api.MultiContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
@Path("rest")
public class RESTService  {

    @Autowired
    DataRetriever dataRetriever;



    @GET
    @Path("multicontext")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MultiContext> getMultiContextsAfterTimestamp(@QueryParam("timestamp") String timestamp) {
        List<MultiContext> result = dataRetriever.getMultiContextsAfterTimestamp(timestamp);
        return result;

    }
}
