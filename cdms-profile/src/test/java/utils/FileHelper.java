/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author nutgaard
 */
public class FileHelper {

    private static Logger logger = Logger.getLogger(FileHelper.class.getName());

    public static String[] readLines(String filename, int numberOfLines) {
        BufferedReader br = null;
        try {
            logger.debug("Opening reader: " + filename);
            File file = new File(filename);
            br = new BufferedReader(new FileReader(file));
            List<String> lines = Lists.newArrayList();
            String line = null;
            int lineCounter = 0;
            logger.debug("Starting to read");
            while ((line = br.readLine()) != null && lineCounter < numberOfLines){
                lines.add(line);
                lineCounter++;
            }
            logger.debug("Read complete");
            return fromList(String.class, lines);
        } catch (Exception ex) {
            logger.fatal("Error while reading:", ex);
            return new String[0];
        } finally {
            try {
                logger.debug("Closing reader: "+filename);
                if (br != null) {
                    br.close();
                }
                else {
                    logger.fatal("Somehow bufferedreader was not inited");
                }
            } catch (IOException ex) {
                logger.fatal("Error while closing bufferedreader:", ex);
            }
        }
    }
    private static <T> T[] fromList(Class<T> cls, List<T> list) {
        T[] array = (T[])Array.newInstance(cls, list.size());
        return list.toArray(array);
    }
    @Test
    public void main() {
        String filename = "./../../performance_short.log";
        String[] lines = readLines(filename, 10);
        assertEquals(10, lines.length);
        for (String line : lines){
            assertNotNull(line);
        }
    }
    @Test
    public void fileNotFound() {
        String filename = "teasidjaijasdasdas.test";
        String[] lines = readLines(filename, 10);
        assertNotNull(lines);
        assertEquals(0, lines.length);
    }
    @Test
    public void fileIsLessThenRequested() {
        String filename = "./../../reallysmall.txt";
        String[] lines = readLines(filename, 10);
        assertNotNull(lines);
        assertEquals(3, lines.length);
    }
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(FileHelper.class.getName());
}
