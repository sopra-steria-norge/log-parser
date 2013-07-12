package no.osl.cdms.profile.web;

import java.io.IOException;
import java.io.StringWriter;

import no.osl.cdms.profile.log.LayoutEntity;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.ProcedureEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;

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

@Path("/")
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

    private final String BAD_INPUT_ERROR_MESSAGE = "415 Unsupported Media Type";
    private final String NOT_FOUND_ERROR_MESSAGE = "404 Not found";

    @GET
    @Path("test")
    @Produces("text/plain")
    public String test() {
        return "hello, world";
    }

    @GET
    @Path("getProcedures")
    @Produces("application/json")
    public String getProcedures() {
        return toJSON(dataRetriever.getAllProcedures());
    }

    @GET
    @Path("getPercentiles/{procedureId}")
    @Produces("application/json")
    public String getPercentiles(@PathParam("procedureId") String procedureId, @QueryParam("from") String from,
                                 @QueryParam("to") String to, @QueryParam("percentages") String percentages) {
        int[] percentagesArray;
        if (percentages == null) {
            percentagesArray = new int[] {10,20,30,40,50,60,70,80,90,100};
        } else {
            String[] tmp = percentages.split(",");
            percentagesArray = new int[tmp.length];
            for (int i=0; i<tmp.length; i++) {
                try {
                    percentagesArray[i] = Integer.parseInt(tmp[i].replace(" ", ""));
                } catch (NumberFormatException e) {
                    logger.debug("Percentage '"+tmp[i]+"' from user input could not be parsed into int. Percentage ignored.");
                }
            }
        }

        try {
            int procedureIdInt = Integer.parseInt(procedureId);
            String[] percentiles = dataRetriever.getPercentileByProcedure(procedureIdInt, from, to, percentagesArray);
            return toJSON(percentiles);

        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            return BAD_INPUT_ERROR_MESSAGE;
        } catch (IllegalArgumentException e) {
            logger.debug("Timestamp '" + from + "' or '" + to +  "' from user input could not be parsed into Date");
            return BAD_INPUT_ERROR_MESSAGE;
        }
    }

    @GET
    @Path("getTimeMeasurementsBetweenDates/{procedureId}")
    @Produces("application/json")
    public String getTimeMeasurementsBetweenDates(@PathParam("procedureId") String procedureId,
                                                  @QueryParam("from") String from, @QueryParam("to") String to) {
        try {
            int procedureIdInt = Integer.parseInt(procedureId);
            return toJSON(dataRetriever.getTimeMeasurementBetweenDatesByProcedure(procedureIdInt, from, to));
        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            return BAD_INPUT_ERROR_MESSAGE;
        } catch (IllegalArgumentException e) {
            logger.debug("Timestamp '" + from + "' or '" + to +  "' from user input could not be parsed into Date");
            return BAD_INPUT_ERROR_MESSAGE;
        }
    }

    /**
     * Returns all LayoutEntity tables from the database.
     */
    @GET
    @Path("getAllLayouts")
    @Produces("application/json")
    public String getAllLayouts() {
        List<LayoutEntity> entities = dataRetriever.getAllLayoutEntities();
        return toJSON(entities);
    }

    /**
     * Returns a map from the database mapping all layout names into their respective IDs.
     * The JSON layout descriptions are not included, and must be retrieved separately by
     * getLayout(id). Alternatively, getAllLayouts can be called which does include
     * the JSON layout descriptions.
     */
    @GET
    @Path("getAllLayoutNames")
    @Produces("application/json")
    public String getAllLayoutNames() {
        Map<String, Integer> names = dataRetriever.getAllLayoutEntityNames();
        return toJSON(names);
    }

    /**
     * Returns a LayoutEntity table by ID.
     * @param id
     * @return
     */
    @GET
    @Path("getLayout/{id}")
    @Produces("application/json")
    public String getLayout(@PathParam("id") String id) {
        try {
            LayoutEntity entity = dataRetriever.getLayoutEntity(Integer.parseInt(id));
            if (entity != null) {
                return toJSON(entity);
            } else {
                return NOT_FOUND_ERROR_MESSAGE;
            }
        } catch (NumberFormatException e) {
            logger.debug("LayoutElement ID '" + id + "' from user input could not be parsed into int");
            return BAD_INPUT_ERROR_MESSAGE;
        }
    }

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
        sb.append("\"id\":").append(idCounter).append(",");
        sb.append("\"procedure\":").append(toJSON(pro)).append(",");
        sb.append("\"multiContext\":").append(toJSON(mc)).append(",");
        sb.append("\"timestamp\":").append(new DateTime().getMillis()).append(",");
        if(pro.getClassName().equals("Total")){
            sb.append("\"duration\":").append((int)(Math.random()*1000+2000));
        }
        else{

            sb.append("\"duration\":").append((int)(Math.random()*1000));
        }
        sb.append("}");
        idCounter++;
        procedureCounter++;
        return sb.toString();
    }
    private String toJSON(Object o) {
        if (o == null) {
            return "{}";
        }
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
