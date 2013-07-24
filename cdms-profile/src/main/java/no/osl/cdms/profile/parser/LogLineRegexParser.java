package no.osl.cdms.profile.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import no.osl.cdms.profile.interfaces.Parser;
import org.springframework.stereotype.Component;

@Component
public class LogLineRegexParser implements Parser {

    private static Pattern objPatt = Pattern.compile("(?:([^=\\[\\]\\}\\{\\s,]*?)=)?([^,\\s]+?)(?:@.+?)?(?:\\[(.*?)\\]|\\{(.*?)\\})(?![\\]\\}])");
    private static Pattern kvPatt = Pattern.compile("([^,;\\[\\]=]+?)[=;]([^,;\\[\\]]+)");

    @Override
    public Map<String, String> process(String s) {
        HashMap<String, String> properties = new HashMap<String, String>();
        return parse(properties, s);
    }

    private Map<String, String> parse(Map<String, String> properties, String text) {
        appendToProperties(properties, "", text);
        if (properties.size() > 0) {
            properties.put("timestamp", getTimestamp(text));
        }
        return properties;
    }

    private void appendToProperties(Map<String, String> properties, String base, String text) {
        String remaining = findObjects(properties, base, text);
        findKeyValuePairs(properties, base, remaining);
    }

    private String findObjects(Map<String, String> properties, String base, String text) {
        if (text == null) {
            return "";
        }
        Matcher objMatch = objPatt.matcher(text);
        while (objMatch.find()) {
            String inner = objMatch.group(3) != null ? objMatch.group(3) : objMatch.group(4);
            String key = objMatch.group(1) != null ? objMatch.group(1) : objMatch.group(2);
            if (objMatch.group(1) != null) {
                properties.put(base + objMatch.group(1), objMatch.group(2));
            }
            appendToProperties(properties, base + key + ".", inner);
        }
        return objMatch.replaceAll("");
    }

    private void findKeyValuePairs(Map<String, String> properties, String base, String text) {
        Matcher kvMatch = kvPatt.matcher(text);
        while (kvMatch.find()) {
            for (int i = 0; i <= kvMatch.groupCount(); i++) {
                properties.put(base + kvMatch.group(1), kvMatch.group(2));
            }
        }
    }

    private String getTimestamp(String line) {
        return line.substring(0, "2013-06-25 15:02:10,063".length());
    }
}
