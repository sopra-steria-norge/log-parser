package no.osl.cdms.profile.web.helpers;

import javax.ws.rs.WebApplicationException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class RestInputParser {
    private Logger logger = Logger.getLogger(getClass().getName());
    
    public int[] parsePercentages(String percentages) {
        int[] percentagesArray;
        if (percentages == null) {
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
        return percentagesArray;
    }

    public int[] parseIdArray(String procedureId) {
        int[] procedureIdInt;
        try {
            String[] splitted = procedureId.split(",");
            procedureIdInt = new int[splitted.length];
            for (int i = 0; i < splitted.length; i++) {
                procedureIdInt[i] = Integer.parseInt(splitted[i]);
            }
        } catch (NumberFormatException e) {
            logger.debug("procedureId '" + procedureId + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        }
        return procedureIdInt;
    }

    public int parseInt(String buckets) {
        int bucketsInt;
        try {
            if (buckets == null) {
                bucketsInt = -1;
            } else {
                bucketsInt = Integer.parseInt(buckets);
            }
        } catch (NumberFormatException e) {
            logger.debug("buckets '" + buckets + "' from user input could not be parsed into int");
            throw new WebApplicationException(415);
        }
        return bucketsInt;
    }
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(RestInputParser.class.getName());
}
