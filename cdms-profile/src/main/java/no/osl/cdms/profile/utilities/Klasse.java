package no.osl.cdms.profile.utilities;

import no.osl.cdms.profile.log.ProcedureEntity;
import no.osl.cdms.profile.log.TimeMeasurementEntity;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.XmlTransient;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: elothe
 * Date: 18.07.13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public class Klasse {

    public Klasse() {

    }


    public String getHidden() {
        return "I am hidden";
    }

    public String getText() {
        return "I am text";
    }

    public static void main(String[] a) {
        DateTime d = new DateTime(0);

        System.out.println(d.toString());
//        System.out.println(new DateTime(d.toString()));
//        Integer.parseInt(null);

    }

    private static String toJSON(Object o) {
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
            throw new IllegalArgumentException();
        }
    }
}
