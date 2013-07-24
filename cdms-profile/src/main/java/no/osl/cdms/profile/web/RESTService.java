package no.osl.cdms.profile.web;

import java.io.StringWriter;

import no.osl.cdms.profile.log.MultiContextEntity;
import no.osl.cdms.profile.log.ProcedureEntity;

import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

@Path("/")
public class RESTService {

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
    @Path("procedure")
    @Produces("application/json")
    public String getProcedures() {
        return toJSON(dataRetriever.getAllProcedures());
    }

    @GET
    @Path("percentile")
    @Produces("application/json")
    public String getPercentiles(@QueryParam("from") String from,
                                 @QueryParam("to") String to, @QueryParam("percentages") String percentages) {
        return getPercentiles(null, from, to, percentages);
    }

    @GET
    @Path("percentile/{procedureId}")
    @Produces("application/json")
    public String getPercentiles(@PathParam("procedureId") String procedureId, @QueryParam("from") String from,
                                 @QueryParam("to") String to, @QueryParam("percentages") String percentages) {
        int[] percentagesArray;
        DateTime fromDate;
        DateTime toDate;
        if (from == null) throw new WebApplicationException(400);

        // Parse percentages
        if (percentages == null) {
            percentagesArray = new int[] {10,20,30,40,50,60,70,80,90};
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

        // Parse dates
        try {
            fromDate = new DateTime(from);
            if (to != null) {
                toDate = new DateTime(to);
            } else {
                toDate = new DateTime();
            }
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal time format '" + from + "' or '" + to + "'");
            throw new WebApplicationException(415);
        }

        // Retrieve percentiles
        try {
            int procedureIdInt = Integer.parseInt(procedureId);
            return toJSON(dataRetriever.getPercentileByProcedure(procedureIdInt, fromDate, toDate, percentagesArray));
        } catch (NumberFormatException e){
            return toJSON(dataRetriever.getPercentile(fromDate,toDate,percentagesArray));
        }
    }

    @GET
    @Path("timeMeasurement/{procedureId}")
    @Produces("application/json")
    public String getTimeMeasurements(@PathParam("procedureId") String procedureId,
                                                  @QueryParam("from") String from, @QueryParam("to") String to,
                                                  @QueryParam("buckets") String buckets) {
        int procedureIdInt, bucketsInt;

        // Parse procedure ID
        try {
            procedureIdInt = Integer.parseInt(procedureId);
        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        }

        // Determine number of buckets
        try{
            if (buckets == null) {
                bucketsInt = -1;
            } else {
                bucketsInt = Integer.parseInt(buckets);
            }
        } catch (NumberFormatException e) {
            logger.debug("buckets '" + buckets + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        }

        // Determine timestamps
        DateTime fromDate, toDate;
        try {
            fromDate = new DateTime(from);
            if (to != null) {
                toDate = new DateTime(to);
            } else {
                toDate = new DateTime();
            }
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal time format '" + from + "' or '" + to + "'");
            throw new WebApplicationException(415);
        } catch (NullPointerException e) {
            throw new WebApplicationException(415);
        }

        // Retrieve and send data
        try {
            return toJSON(dataRetriever.getTimeMeasurements(procedureIdInt, fromDate, toDate, bucketsInt));
        } catch (NoSuchElementException e) {
            throw new WebApplicationException(404);
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
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
////        System.out.println("Autowired: "+dataRetriever);
////        String response = toJSON(dataRetriever.getMultiContextsAfterTimestamp(new DateTime().toString()));
////        System.out.println("Serialized data: "+response);
//        JaxRSThis jrt = new JaxRSThis(this);
//        resp.getWriter().write(jrt.invoke(req, resp));
////        StringBuilder sb = new StringBuilder();
////        sb.append("[");
////        List<TimeMeasurementEntity> tms = new ArrayList<TimeMeasurementEntity>();
////        for (int i = 0; i < 6; i++) tms.add(createSingle());
////
////        resp.getWriter().write(toJSON(tms));
////        procedureCounter = 1;
////        multiContextCounter++;
//    }
    private TimeMeasurementEntity createSingle() {
        MultiContextEntity mc = new MultiContextEntity(new DateTime("2013-06-25T01:15:52.458Z").toDate(),
                new DateTime("2013-06-25T01:15:52.578Z").toDate());
        ProcedureEntity pro = procedures[procedureCounter - 1];
        pro.setId(procedureCounter);
        mc.setId(multiContextCounter);
        TimeMeasurementEntity tm = new TimeMeasurementEntity(pro, mc, new DateTime().toDate(),
                new Duration((int)(Math.random()*1000)).toString());
        if(pro.getClassName().equals("Total")){
            tm.setDuration(new Duration((int) (Math.random() * 1000 + 2000)).toString());
        }
        idCounter++;
        procedureCounter++;
        return tm;
    }

    private String toJSON(Object o) {
        if (o == null) {
            return "{}";
        }
        try {
            StringWriter writer = new StringWriter();
            ObjectMapper mapper = new ObjectMapper();
            JsonGenerator jsonGenerator = new MappingJsonFactory().createJsonGenerator(writer);
            DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z',SSS");
            mapper.setDateFormat(myDateFormat);
            mapper.writeValue(writer, o);
            return writer.toString();
        } catch (Exception ex) {
            logger.fatal(null, ex);
            throw new IllegalArgumentException();
        }
    }
}
