package no.osl.cdms.profile.utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogWriter {

    private static String inputFilePath = "C:/Program Files/fuse-esb-7.1.0.fuse-047/data/log/input.log";
    private static String outputFilePath = "C:/Program Files/fuse-esb-7.1.0.fuse-047/data/log/performance.log";

    public static void main(String[] args) throws IOException {
        final FileWriter writer = new FileWriter(outputFilePath, true);
        final PrintWriter printWriter = new PrintWriter(writer, true);
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilePath));
        DateTime lastPrintLog, thisPrintLog;
        DateTime lastPrintSystem, thisPrintSystem;

        String line = bufferedReader.readLine();
        if (line == null || line.equals("")) {
            System.exit(0);
        }
        lastPrintLog = parseDateString(line.substring(0, 23));
        lastPrintSystem = new DateTime();
        printWriter.println(fromLogDateTimeToSystemLogTimeFormat(lastPrintSystem) + line.substring(23));

        while (true) {
            line = bufferedReader.readLine();

            if (line != null && !line.equals("")) {
                thisPrintLog = parseDateString(line.substring(0, 23));
                while (true) {
                    while (!thisPrintLog.isBefore(lastPrintLog.plus(new DateTime().minus(lastPrintSystem.getMillis()).getMillis()))) {
                        Thread.yield();
                    }
                    thisPrintSystem = new DateTime();
                    printWriter.println(fromLogDateTimeToSystemLogTimeFormat(thisPrintSystem) + line.substring(23));

                    lastPrintLog = thisPrintLog;
                    lastPrintSystem = thisPrintSystem;
                    break;
                }
            } else {
                System.exit(0);
            }
        }
    }

    private static String fromLogDateTimeToSystemLogTimeFormat(DateTime logDateTime) {
        String k = logDateTime.toString().substring(0, 23).replaceAll("T", " ").replaceAll("\\.", ",");
        System.out.println("Next line to log: "+k);
        return k;
    }

    public static DateTime parseDateString(String string) {

        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd kk:mm:ss,SSS", Locale.ENGLISH).parse(string);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
        return dtf.parseDateTime(string);
    }
}