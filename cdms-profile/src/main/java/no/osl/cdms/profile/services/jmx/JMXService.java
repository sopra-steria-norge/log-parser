package no.osl.cdms.profile.services.jmx;

import com.google.common.collect.Lists;
import java.io.StringWriter;

import java.util.List;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.persistence.ProcedureEntity;
import no.osl.cdms.profile.persistence.TimeMeasurementEntity;
import no.osl.cdms.profile.services.DataRetriever;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.jmx.export.annotation.*;

@ManagedResource(description="CDMS profile MBean")
public class JMXService {
    private DateTime icw_snapshot_time = new DateTime(), tsatcalc_snapshot_time = new DateTime();
    private long icw_snapshot = 0, tsatcalc_snapshot = 0;
    private int snapshot_update_rate = 10000;
    private int lastSeconds = 20;

    @Autowired
    DataRetriever dataRetriever;
    Logger logger = Logger.getLogger(getClass().getName());

    @ManagedAttribute
     public long getICW() {

        if (new DateTime().minusMillis(snapshot_update_rate).isBefore(icw_snapshot_time)) {
            return icw_snapshot;
        }
        TimeMeasurement maxDurationTimeMeasurement = dataRetriever.getMaxDurationTimeMeasurement(18, new DateTime().minusSeconds(lastSeconds), new DateTime());
        icw_snapshot_time = new DateTime();

        if (maxDurationTimeMeasurement == null) {
            return icw_snapshot;
        }

        icw_snapshot = new Duration(maxDurationTimeMeasurement.getDuration()).getMillis();
        return icw_snapshot;
    }

    @ManagedAttribute
     public long getTSATCalc() {

        if (new DateTime().minusMillis(snapshot_update_rate).isBefore(tsatcalc_snapshot_time)) {
            return tsatcalc_snapshot;
        }
        TimeMeasurement maxDurationTimeMeasurement = dataRetriever.getMaxDurationTimeMeasurement(14, new DateTime().minusSeconds(lastSeconds), new DateTime());
        tsatcalc_snapshot_time = new DateTime();

        if (maxDurationTimeMeasurement == null) {
            return tsatcalc_snapshot;
        }

        tsatcalc_snapshot = new Duration(maxDurationTimeMeasurement.getDuration()).getMillis();
        return tsatcalc_snapshot;
    }

    @ManagedOperation(description="All procedures")
    public String procedures() {
        return toJSON(dataRetriever.getAllProcedures());
    }
    @ManagedOperation(description = "Timemeasurements for Total for the last 24 hours")
    public String timemeasurements_total_24hours() {
        Procedure total = new ProcedureEntity("Total", "Total", null);
        return procedureMeasurements(total, 24);
    }

    @ManagedOperation(description = "Timemeasurements for TsatCalculatorServiceImpl.calculateTtotAndTsatFor the last 24 hours")
    public String timemeasurements_TSATCalc_24hours() {
        Procedure tsatCalc = new ProcedureEntity("Tsat.calc", "TsatCalculatorServiceImpl","calculateTtotAndTsatFor");
        return procedureMeasurements(tsatCalc, 24);
    }

    @ManagedOperation(description = "Percentiles for Total (10,20,30,40,50,60,70,80,90) for the last 24 hours")
    public String percentiles_total_24hours() {
        Procedure total = new ProcedureEntity("Total", "Total", null);
        return procedurePercentiles(total, 24);
    }

    @ManagedOperation(description = "Percentiles for TsatCalculatorServiceImpl.calculateTtotAndTsatFor the last 24 hours (10,20,30,40,50,60,70,80,90)")
    public String percentiles_TSATCalc_24hours() {
        Procedure tsatCalc = new ProcedureEntity("Tsat.calc", "TsatCalculatorServiceImpl","calculateTtotAndTsatFor");
        return procedurePercentiles(tsatCalc, 24);
    }

    @ManagedOperation(description = "Percentiles for Total (10,20,30,40,50,60,70,80,90) for the last 72 hours")
    public String percentiles_total_72hours() {
        Procedure total = new ProcedureEntity("Total", "Total", null);
        return procedurePercentiles(total, 72);
    }

    @ManagedOperation(description = "Percentiles for TsatCalculatorServiceImpl.calculateTtotAndTsatFor the last 72 hours (10,20,30,40,50,60,70,80,90)")
    public String percentiles_TSATCalc_72hours() {
        Procedure tsatCalc = new ProcedureEntity("Tsat.calc", "TsatCalculatorServiceImpl","calculateTtotAndTsatFor");
        return procedurePercentiles(tsatCalc, 72);
    }

    @ManagedOperation(description = "Percentiles for Total (10,20,30,40,50,60,70,80,90) for the last 7 days")
    public String percentiles_total_7days() {
        Procedure total = new ProcedureEntity("Total", "Total", null);
        return procedurePercentiles(total, 168);
    }

    @ManagedOperation(description = "Percentiles for TsatCalculatorServiceImpl.calculateTtotAndTsatFor the last 7 days (10,20,30,40,50,60,70,80,90)")
    public String percentiles_TSATCalc_7days() {
        Procedure tsatCalc = new ProcedureEntity("Tsat.calc", "TsatCalculatorServiceImpl","calculateTtotAndTsatFor");
        return procedurePercentiles(tsatCalc, 168);
    }

    @ManagedOperation(description="Percentiles for all procedures")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "from", description = "From date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "to", description = "To date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "percentages", description = "List of percentages, eg 10,20,90")})
    public String percentiles(String from, String to, String percentages) {
        return percentiles(null, from, to, percentages);
    }

    @ManagedOperation(description="Percentiles for all procedures")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "procedureId", description = "ID if procedure"),
            @ManagedOperationParameter(name = "from", description = "From date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "to", description = "To date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "percentages", description = "List of percentages, eg 10,20,90")})
    public String percentiles(String procedureId, String from, String to, String percentages) {
        int[] percentagesArray;
        DateTime fromDate;
        DateTime toDate;
        if (from == null || from.equals("")) {
            throw new IllegalArgumentException();
        }

        // Parse percentages
        if (percentages == null || percentages.equals("")) {
            percentagesArray = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90};
        } else {
            String[] tmp = percentages.split(",");
            percentagesArray = new int[tmp.length];
            for (int i = 0; i < tmp.length; i++) {
                try {
                    percentagesArray[i] = Integer.parseInt(tmp[i].replace(" ", ""));
                } catch (NumberFormatException e) {
                    logger.debug("Percentage '" + tmp[i] + "' from user input could not be parsed into int. Percentage ignored.");
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
            throw e;
        }

        // Retrieve percentiles
        try {
            int procedureIdInt = Integer.parseInt(procedureId);
            return toJSON(dataRetriever.getPercentileByProcedure(procedureIdInt, fromDate, toDate, percentagesArray));
        } catch (NumberFormatException e) {
            return toJSON(dataRetriever.getPercentile(fromDate, toDate, percentagesArray));
        }
    }

    @ManagedOperation(description="Timemeasurements of a procedure")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "procedureId", description = "ID if procedure"),
            @ManagedOperationParameter(name = "from", description = "From date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "to", description = "To date, format 2013-07-18T23:50:58.359+02:00"),
            @ManagedOperationParameter(name = "buckets", description = "Number of buckets(splits timemeasurements into buckets, return one timemeasurement for each bucket")})
    public String timeMeasurements(String procedureId, String from, String to, String buckets) {
        int[] procedureIdInt;
        int bucketsInt;

        // Parse procedure ID
        try {
            String[] splitted = procedureId.split(",");
            procedureIdInt = new int[splitted.length];
            for (int i = 0; i < splitted.length; i++) {
                procedureIdInt[i] = Integer.parseInt(splitted[i]);
            }
        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            throw e;
        }

        // Determine number of buckets
        try {
            if (buckets == null || buckets.equals("")) {
                bucketsInt = -1;
            } else {
                bucketsInt = Integer.parseInt(buckets);
            }
        } catch (NumberFormatException e) {
            logger.debug("buckets '" + buckets + "' from user input could not be parsed into int");
            throw e;
        }

        // Determine timestamps
        DateTime fromDate, toDate;
        try {
            fromDate = new DateTime(from);
            if (to != null && !to.equals("")) {
                toDate = new DateTime(to);
            } else {
                toDate = new DateTime();
            }
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal time format '" + from + "' or '" + to + "'");
            throw e;
        } catch (NullPointerException e) {
            throw e;
        }

        // Retrieve and send data
        try {
            List<TimeMeasurement> out = Lists.newLinkedList();
            for (int id : procedureIdInt){
                out.addAll(dataRetriever.getTimeMeasurements(id, fromDate, toDate, bucketsInt));
            }
            return toJSON(out);
        } catch (NoSuchElementException e) {
            return null;
        }
    }


    private String procedureMeasurements(Procedure p, int lastHours) {
        for (Procedure p2 : dataRetriever.getAllProcedures()) {
            if (p2.equals(p)) {
                return toJSON(dataRetriever.getTimeMeasurements(p2.getId(), new DateTime().minusHours(lastHours), new DateTime()));
            }
        }
        return "[]";
    }

    private String procedurePercentiles(Procedure p, int lastHours) {
        for (Procedure p2 : dataRetriever.getAllProcedures()) {
            if (p2.equals(p)) {
                return toJSON(dataRetriever.getPercentileByProcedure(p2.getId(), new DateTime().minusHours(lastHours),
                        new DateTime(), new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}));
            }
        }
        return "[]";
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
