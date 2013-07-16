///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package no.osl.cdms.profile.web;
//
//import com.google.common.collect.Maps;
//import java.io.StringWriter;
//import java.lang.reflect.Method;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Path;
//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.map.MappingJsonFactory;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.stereotype.Component;
//
///**
// *
// * @author nutgaard
// */
//@Component
//public class JaxRSThis {
//
//    private Object workon;
//    private Map<Pattern, Method> routes;
//
//    public JaxRSThis(Object workon) {
//        this.workon = workon;
//        this.routes = Maps.newConcurrentMap();
//        parse();
//    }
//
//    private void parse() {
//        String base = workon.getClass().getAnnotation(Path.class).value();
//        for (Method method : workon.getClass().getMethods()) {
//            Path path;
//            if ((path = method.getAnnotation(Path.class)) != null) {
//                String route = join('/', base, path.value());
//                route = route.replaceAll("\\{[^\\}]+\\}", "([a-z]+)");
//                routes.put(Pattern.compile(route), method);
//            }
//        }
//    }
//
//    private static String join(char delimiter, String... elements) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(delimiter);
//        for (String e : elements) {
//            while (e.startsWith("" + delimiter)) {
//                e = e.substring(1);
//            }
//            while (e.endsWith("" + delimiter)) {
//                e = e.substring(0, e.length() - 1);
//            }
//            sb.append(e).append(delimiter);
//        }
//        return sb.toString();
//    }
//
//    public String invoke(HttpServletRequest req, HttpServletResponse resp) {
//        String uri = req.getRequestURI();
//        if (!uri.endsWith("/")) {
//            uri += "/";
//        }
//        for (Entry<Pattern, Method> route : routes.entrySet()) {
//            Matcher m = route.getKey().matcher(uri);
//            if (m.matches()) {
//                System.out.println("Matched: " + route.getKey().pattern() + " groups: " + m.groupCount());
//                Object[] args = new Object[m.groupCount()];
//                for (int i = 0; i < args.length; i++) {
//                    args[i] = m.group(i+1);
//                }
//                try {
//                    Object obj = null;
//                    if (args.length == 0) {
//                        System.out.println("Invoking "+route.getValue()+" on "+workon);
//                        obj = route.getValue().invoke(workon);
//                    } else {
//                        obj = route.getValue().invoke(workon, args);
//                    }
//                    return toJSON(obj);
//                } catch (Exception ex) {
//                    throw new IllegalArgumentException(ex);
//                }
//            }
//        }
//        return "¤=¤ 404 ¤=¤";
//    }
//
//    private String toJSON(Object o) {
//        if (o instanceof String)return (String)o;
//        if (o == null) {
//            return "{}";
//        }
//        try {
//            StringWriter writer = new StringWriter();
//            ObjectMapper mapper = new ObjectMapper();
//            JsonGenerator jsonGenerator = new MappingJsonFactory().createJsonGenerator(writer);
//            DateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z',SSS");
//            mapper.setDateFormat(myDateFormat);
//            mapper.writeValue(writer, o);
//            return writer.toString();
//        } catch (Exception ex) {
//            throw new IllegalArgumentException();
//        }
//    }
//}
