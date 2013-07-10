package no.osl.cdms.profile.web;

import java.io.IOException;
import java.io.StringWriter;

import no.osl.cdms.profile.api.MultiContext;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.ProcedureEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

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
public class RESTService extends HttpServlet {
    static int idCounter = 1;
    static int procedureCounter = 1;
    static int multiContextCounter = 1;
    static ProcedureEntity[] procedures = {new ProcedureEntity(null, "Total", null),
            new ProcedureEntity(null, "IcwMessageProcessorBean", "process"),
            new ProcedureEntity(null, "FlightServiceImpl", "process"),
            new ProcedureEntity(null, "Wait", null),
            new ProcedureEntity(null, "Milestone", "execute"),
            new ProcedureEntity(null, "UpdateMessageFactory", "createUpdatesForPublishing")};
    @Autowired
    DataRetriever dataRetriever;

    Logger logger = Logger.getLogger(getClass().getName());
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
//        System.out.println("Autowired: "+dataRetriever);
//        String response = toJSON(dataRetriever.getMultiContextsAfterTimestamp(new DateTime().toString()));
//        System.out.println("Serialized data: "+response);

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < 6; i++)sb.append(createSingle()).append(",");
        sb.replace(sb.length()-1, sb.length(), "]");
        resp.getWriter().write(sb.toString());
        procedureCounter = 1;
        multiContextCounter++;
    }
    private String createSingle() {
        MultiContextEntity mc = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.578Z").toDate());
        ProcedureEntity pro = procedures[procedureCounter - 1];
        pro.setId(procedureCounter);
        mc.setId(multiContextCounter);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("id:").append(idCounter).append(",");
        sb.append("procedure:").append(toJSON(pro)).append(",");
        sb.append("multiContext:").append(toJSON(mc)).append(",");
        sb.append("timestamp:").append(new DateTime().plusMillis(idCounter).getMillis()).append(",");
        sb.append("duration:'").append((int)(Math.random()*1000)).append("'");
        sb.append("}");
        idCounter++;
        procedureCounter++;
        return sb.toString();
    }
    private  String toJSON(Object o) {
        try {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            JsonGenerator jsonGenerator = new MappingJsonFactory().createJsonGenerator(writer);
            mapper.writeValue(writer, o);
            return writer.toString();
        } catch (Exception ex) {
            logger.fatal(null, ex);
            throw new IllegalArgumentException();
        }
    }
}
