/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.parser;

import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nutgaard
 */
public class PerformanceTest {
    private long prev;

    private List<String> lines;

    public PerformanceTest() {
    }

    @Before
    public void setup() throws Exception {
        long startRead = System.currentTimeMillis();
        lines = FileUtils.readLines(new File("./../../performance_short.log"));
        timestamp(startRead);
    }

    /**
     * Test of parse method, of class Parser.
     */
//    @Test
    public void localMockLogLine() {
        LogLineRegexParser parser = new LogLineRegexParser();
        long i = 0;
        long startParse = System.currentTimeMillis();
        prev = System.currentTimeMillis();
        for (String l : lines) {
            parser.parse(l);
            modDebug(i++, prev);
        }
        timestamp(startParse);
    }
    private void timestamp(long start) {
        System.out.println("Time "+(System.currentTimeMillis()-start)+"ms");
    }
    private void modDebug(long i, long prev){
        if (i%1000 == 0){
            System.out.print("Line "+i+" parsed at ");
            timestamp(prev);
            prev = System.currentTimeMillis();
        }
    }
}