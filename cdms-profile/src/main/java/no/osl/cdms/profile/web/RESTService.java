package no.osl.cdms.profile.web;

import com.google.common.collect.Lists;
import java.io.StringWriter;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.*;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.web.helpers.RestInputParser;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

@Path("/")
public class RESTService {

    @Autowired
    private DataRetrieverImpl dataRetriever;
    @Autowired
    private RestInputParser inputparser;
    Logger logger = Logger.getLogger(getClass().getName());

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

        DateTime fromDate;
        DateTime toDate;
        int procedureIdInt = -1;
        int[] percentagesArray = inputparser.parsePercentages(percentages);

        if (from == null) {
            logger.error("percentile/{procedureId}::400 No from date found");
            throw new WebApplicationException(400);
        }

        try {
            fromDate = new DateTime(from);
            toDate = (to != null) ? new DateTime(to) : new DateTime();
            procedureIdInt = (procedureId != null) ? Integer.parseInt(procedureId) : -1;
        } catch (Exception e) {
            logger.error("percentile/{procedureId}::415: " + e.getMessage());
            throw new WebApplicationException(415);
        }

        if (procedureIdInt == -1) {
            return toJSON(dataRetriever.getPercentile(fromDate, toDate, percentagesArray));
        } else {
            return toJSON(dataRetriever.getPercentileByProcedure(procedureIdInt, fromDate, toDate, percentagesArray));
        }
    }

    @GET
    @Path("timeMeasurement/{procedureId}")
    @Produces("application/json")
    public String getTimeMeasurements(@PathParam("procedureId") String procedureId,
            @QueryParam("from") String from, @QueryParam("to") String to,
            @QueryParam("buckets") String buckets) {
        
        
        int[] procedureIdInt = inputparser.parseIdArray(procedureId);
        int bucketsInt = inputparser.parseInt(buckets);

        DateTime fromDate, toDate;
        try {
            fromDate = new DateTime(from);
            toDate = (to != null) ? new DateTime(to) : new DateTime();
        } catch (Exception e) {
            logger.error("timeMeasurement/{procedureId}::415: " + e.getMessage());
            throw new WebApplicationException(415);
        }

        try {
            List<TimeMeasurement> out = Lists.newLinkedList();
            for (int id : procedureIdInt) {
                out.addAll(dataRetriever.getTimeMeasurements(id, fromDate, toDate, bucketsInt));
            }
            return toJSON(out);
        } catch (Exception e) {
            logger.error("timeMeasurement/{procedureId}::400: " + e.getMessage());
            throw new WebApplicationException(400);
        }
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
