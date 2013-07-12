package no.osl.cdms.profile.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import no.osl.cdms.profile.api.TimeMeasurement;
import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

@Path("rest")
public class RESTService extends HttpServlet{

    @Autowired
    DataRetriever dataRetriever;

    Logger logger = Logger.getLogger(getClass().getName());

    static int idCounter = 1;
    static int procedureCounter = 1;
    static int multiContextCounter = 1;
    static ProcedureEntity[] procedures = {new ProcedureEntity(null, "Total", null),
            new ProcedureEntity(null, "IcwMessageProcessorBean", "process"),
            new ProcedureEntity(null, "FlightServiceImpl", "process"),
            new ProcedureEntity(null, "Wait", null),
            new ProcedureEntity(null, "Milestone", "execute"),
            new ProcedureEntity(null, "UpdateMessageFactory", "createUpdatesForPublishing")};


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
        DateTime fromDate;
        DateTime toDate;
        try {
            fromDate = new DateTime(from);
            toDate = new DateTime(to);
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal time format '" + from + "' or '" + to + "'");
            throw new WebApplicationException(415);
        }

        try {
            int procedureIdInt = Integer.parseInt(procedureId);


            String[] percentiles = dataRetriever.getPercentileByProcedure(procedureIdInt, fromDate, toDate, percentagesArray);
            return toJSON(percentiles);

        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        } catch (IllegalArgumentException e) {
            logger.debug("Timestamp '" + from + "' or '" + to +  "' from user input could not be parsed into Date");
            throw new WebApplicationException(415);
        }
    }

    @GET
    @Path("getTimeMeasurementsBetweenDates/{procedureId}")
    @Produces("application/json")
    public String getTimeMeasurementsBetweenDates(@PathParam("procedureId") String procedureId,
                                                  @QueryParam("from") String from, @QueryParam("to") String to) {
        try {
            int procedureIdInt = Integer.parseInt(procedureId);
            return toJSON(dataRetriever.getTimeMeasurementBetweenDatesByProcedure(procedureIdInt, new DateTime(from), new DateTime(to)));
        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal time format '" + from + "' or '" + to + "'");
            throw new WebApplicationException(415);
        }
    }

    @GET
    @Path("getLayout/{page}")
    @Produces("application/json")
    public String getLayout(@PathParam("page") String page){
        if (page.equals("24hours")){
            return "{\"elements\":[{\"type\":\"div\",\"classes\":\"hero-unit\",\"elements\":[{\"type\":\"h1\",\"elements\":[{\"type\":\"p\",\"data\":{\"innerHTML\":\"Test\"}}],\"data\":{\"innerHTML\":\"JPB-JSONPageBuilder\"}},{\"type\":\"p\",\"data\":{\"innerHTML\":\"Alightweightandsmallframeworkfordefininghtmlinjson\"}}]},{\"type\":\"div\",\"classes\":\"row\",\"id\":\"\",\"elements\":[{\"type\":\"graph\",\"classes\":\"span4\",\"id\":\"\",\"data\":{\"graphOf\":[\"ICWThingy\"]}},{\"type\":\"graph\",\"classes\":\"span4\",\"id\":\"\",\"data\":{\"graphOf\":[\"TSATCalculator\"]}},{\"type\":\"graph\",\"classes\":\"span4\",\"id\":\"\",\"data\":{\"graphOf\":[\"ICWThingy\",\"TSATCalculator\"]}}]},{\"type\":\"div\",\"classes\":\"row\",\"id\":\"\",\"elements\":[{\"type\":\"percentileTable\",\"classes\":\"\",\"id\":\"\",\"data\":{\"percentiles\":{\"of\":[\"ICWThingy\",\"TSATCalculator\"],\"values\":[100,90,80,0],\"limits\":{\"ICWThingy\":[11,10,10,10],\"TSATCalculator\":[10,10,10,10]}},\"tablestyle\":\"tabletable-striped\",\"tableheaderstyle\":\"\",\"tablerowstyle\":\"\",\"tablecellstyle\":\"\"}}]}]}";
        }

        return "";
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
        resp.getWriter().write(req.getRequestURI());
        if (req.getRequestURI().equals("/rest/getLayout/24hours")){
            resp.getWriter().write(getLayout("24hours"));

        }
        else if(req.getRequestURI().equals("/rest/getLayout/week")){
            resp.getWriter().write(getLayout("week"));
        }
        else{
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List<TimeMeasurementEntity> tms = new ArrayList<TimeMeasurementEntity>();
            for (int i = 0; i < 6; i++) tms.add(createSingle());

            resp.getWriter().write(toJSON(new DateTime(tms.get(0).getTimestamp()).toString()));
            procedureCounter = 1;
            multiContextCounter++;

        }
    }
    private TimeMeasurementEntity createSingle() {
        MultiContextEntity mc = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.578Z").toDate());
        ProcedureEntity pro = procedures[procedureCounter - 1];
        pro.setId(procedureCounter);
        mc.setId(multiContextCounter);
        TimeMeasurementEntity tm = new TimeMeasurementEntity(pro, mc, new DateTime().toDate(), "" + new Duration((int)Math.random()*1000).toString());
        if(pro.getClassName().equals("Total")){
            tm.setDuration((int) (Math.random() * 1000 + 2000) + "");
        }
        idCounter++;
        procedureCounter++;
        return tm;
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
