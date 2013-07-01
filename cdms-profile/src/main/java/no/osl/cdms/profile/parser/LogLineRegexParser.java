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
public class LogLineRegexParser {

    private static Pattern objPatt = Pattern.compile("(?:([^=\\[\\]\\}\\{\\s,]*?)=)?([^,\\s]+?)(?:\\[(.*?)\\]|\\{(.*?)\\})(?![\\]\\}])");
    private static Pattern kvPatt = Pattern.compile("([^,;\\[\\]=]+?)[=;]([^,;\\[\\]]+)");

    public Map<String, String> parse(String logline) {
        HashMap<String, String> properties = new HashMap<String, String>();
        appendToProperties(properties, "", logline);
        return properties;
    }

    private void appendToProperties(HashMap<String, String> properties, String base, String obj) {
        Matcher objMatch = objPatt.matcher(obj);
        while (objMatch.find()) {
            String inner = objMatch.group(3) != null ? objMatch.group(3) : objMatch.group(4);
            String key = objMatch.group(1) != null ? objMatch.group(1) : objMatch.group(2);
            if (objMatch.group(1) != null) {
                properties.put(base + objMatch.group(1), objMatch.group(2));
            }
            appendToProperties(properties, base + key + ".", inner);
        }
        Matcher kvMatch = kvPatt.matcher(objMatch.replaceAll(""));
        while (kvMatch.find()) {
            for (int i = 0; i <= kvMatch.groupCount(); i++) {
                properties.put(base + kvMatch.group(1), kvMatch.group(2));
            }
        }
    }
}
