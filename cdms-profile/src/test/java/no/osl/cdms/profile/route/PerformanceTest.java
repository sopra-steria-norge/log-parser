/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.route;

import no.osl.cdms.profile.route.ParserImpl;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import no.osl.cdms.profile.interfaces.Parser;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author nutgaard
 */
public class PerformanceTest {
    private Long prev;
    private List<Long> avg;

    private List<String> lines;

    public PerformanceTest() {
    }

    @Before
    public void setup() throws Exception {
        avg = Lists.newLinkedList();
        long startRead = System.currentTimeMillis();
        lines = FileUtils.readLines(new File("C:/data/input.log"));
        timestamp(startRead);
    }

    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void localMockLogLine() {
        Parser parser = new ParserImpl();
        long i = 0;
        long startParse = System.currentTimeMillis();
        prev = System.currentTimeMillis();
        for (String l : lines) {
            parser.process(l);
            if (modDebug(i++, prev))prev = System.currentTimeMillis();
            
        }
        timestamp(startParse);
        avgCalc();
    }
    private void avgCalc() {
        long sum = 0;
        for (long l : avg){
            sum += l;
        }
        System.out.println("AVG Time: "+(sum/avg.size())+"ms");
    }
    private void timestamp(long start) {
        long t = System.currentTimeMillis() - start;
        avg.add(t);
        System.out.println("Time "+t+"ms");
    }
    private boolean modDebug(long i, long prev){
        if (i%1000 == 0){
            System.out.print("Line "+i+" parsed at ");
            timestamp(prev);
            return true;
        }
        return false;
    }
}