package no.osl.cdms.profile.routes.components;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import no.osl.cdms.profile.interfaces.Parser;
import org.springframework.stereotype.Component;

@Component
public class ParserImpl implements Parser {

    private static final Pattern LTCPattern = Pattern.compile("id=([^,]+),duration=([^,\\[\\{]+)");
    private static final String repetition = "(?:[;,]([^=]+)=([^;\\]\\},;]+))?";
    private static final Pattern MTCPattern = Pattern.compile("(\\w+)\\{([^=]+)=([^;\\]\\}]+)" + repetition + repetition + repetition + repetition + repetition + repetition + "\\}");
    private static final int timestampLength = "2013-06-25 15:02:10,063".length();

    private static void appendTimestamp(Map<String, String> map, String obj) {
        String timestamp = obj.substring(0, timestampLength);
        map.put("timestamp", timestamp);
    }

    private static boolean isMultiThreadContext(String obj) {
        return obj.contains("MultiThreadContext");
    }

    private static boolean isLocalThreadContext(String obj) {
        return obj.contains("LocalThreadContext");
    }

    private static void handleMultiThreadContext(Map<String, String> map, String obj) {
        Matcher m = MTCPattern.matcher(obj);
        while (m.find()) {
            String prefix = m.group(1);
            for (int i = 2; i <= m.groupCount(); i += 2) {
                String first = m.group(i), second = m.group(i + 1);
                if ("null".equals(first) || "null".equals(second) || first == null || second == null) {
                    break;
                }
                map.put("MultiThreadContext." + prefix + "." + first, second);
            }
        }
    }

    private static void handleLocalThreadContext(Map<String, String> map, String obj) {
        Matcher m = LTCPattern.matcher(obj);
        while (m.find()) {
            if (m.groupCount() != 2) {
                throw new UnknownError("Excepted two (found " + m.groupCount() + " ) matching groups in: " + obj);
            }
            map.put("LocalThreadContext.id", m.group(1));
            map.put("LocalThreadContext.duration", m.group(2));
        }
    }

    @Override
    public Map<String, String> process(String s) {
        HashMap<String, String> m = Maps.newHashMap();
        if (s == null || s.length() < timestampLength) {
            return m;
        }
        return parse(m, s);
    }

    private Map<String, String> parse(Map<String, String> properties, String obj) {
        if (isMultiThreadContext(obj)) {
            handleMultiThreadContext(properties, obj);
        } else if (isLocalThreadContext(obj)) {
            handleLocalThreadContext(properties, obj);
        } else {
            return properties;
        }
        if (!properties.isEmpty()) {
            appendTimestamp(properties, obj);
        }
        return properties;
    }
}
