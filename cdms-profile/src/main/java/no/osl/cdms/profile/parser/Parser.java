/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nutgaard
 */
public class Parser {

    private static Pattern objectPattern = Pattern.compile("(?:([^=\\[\\]\\s,]*?)=)?([^,\\s]+?)(?:\\[(.*?)\\](?!\\])|\\{(.*?)\\}(?!\\}))");
    private static Pattern keyValuePattern = Pattern.compile("([^,\\[\\]=]+?)=([^,\\[\\]]+)");

    public Map<String, String> parse(String logline) {
        HashMap<String, String> properties = new HashMap<String, String>();
        appendToProperties(properties, "", logline);
        return properties;
    }

    private void appendToProperties(HashMap<String, String> properties, String base, String obj) {
        Matcher objectMatcher = objectPattern.matcher(obj);
        while (objectMatcher.find()) {
            String inner = objectMatcher.group(3) != null ? objectMatcher.group(3) : objectMatcher.group(4);
            String key = objectMatcher.group(1) != null ? objectMatcher.group(1) : objectMatcher.group(2);
            if (objectMatcher.group(1) != null) {
                properties.put(base + objectMatcher.group(1), objectMatcher.group(2));
            }
            appendToProperties(properties, base + key + ".", inner);
        }
        Matcher keyValueMatcher = keyValuePattern.matcher(objectMatcher.replaceAll(""));
        while (keyValueMatcher.find()) {
            for (int i = 0; i <= keyValueMatcher.groupCount(); i++) {
                properties.put(base + keyValueMatcher.group(1), keyValueMatcher.group(2));
            }
        }
    }
}
