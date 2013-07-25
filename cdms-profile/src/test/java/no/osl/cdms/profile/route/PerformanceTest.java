/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.route;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import java.util.Map;
import no.osl.cdms.profile.interfaces.Parser;
import no.osl.cdms.profile.interfaces.db.Procedure;
import no.osl.cdms.profile.interfaces.db.TimeMeasurement;
import no.osl.cdms.profile.log.LogRepository;
import no.osl.cdms.profile.interfaces.EntityFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.junit.Assert.*;

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
        EntityFactory factory = new EntityFactoryImpl();
        LogRepository repo = mock(LogRepository.class);
        when(repo.getEqualPersistedProcedure(any(Procedure.class))).then(new Answer<Procedure>() {
            @Override
            public Procedure answer(InvocationOnMock invocation) throws Throwable {
                return (Procedure) invocation.getArguments()[0];
            }
        });
        factory.setLogRepository(repo);

        long i = 0;
        long startParse = System.currentTimeMillis();

        long parseTime = 0, factoryTime = 0, prev = System.currentTimeMillis();

        for (String l : lines) {
            long s = System.currentTimeMillis();
            Map<String, String> map = parser.process(l);            
            long p = System.currentTimeMillis();
            List<TimeMeasurement> list = factory.process(map);
            long f = System.currentTimeMillis();

            validateList(list);
            
            parseTime += p-s;
            factoryTime += f-p;
            
            if (modDebug(i++, prev)) {
                System.out.println("Parser: " + parseTime);
                System.out.println("Factory: " + factoryTime);
                parseTime = 0;
                factoryTime = 0;
                prev = System.currentTimeMillis();
            }

        }
        timestamp(startParse);
        System.out.println("AVGPerLine: " + ((System.currentTimeMillis() - startParse) / (lines.size() * 1.0)));
        avgCalc();
    }
    
    private void validateList(List<TimeMeasurement> list) {
        for (TimeMeasurement tm : list) {
            assertNotNull(tm.getDuration());
            assertTrue(tm.getDuration().length() > 0);
            assertNotNull(tm.getProcedure());
            assertNotNull(tm.getTimestamp());
            
            Procedure p = tm.getProcedure();
            assertNotNull(p.getClassName());
            assertTrue(p.getClassName().length() > 0);
        }
    }

    private void avgCalc() {
        long sum = 0;
        for (long l : avg) {
            sum += l;
        }
        System.out.println("AVG Time: " + (sum / avg.size()) + "ms");
    }

    private void timestamp(long start) {
        long t = System.currentTimeMillis() - start;
        avg.add(t);
        System.out.println("Time " + t + "ms");
    }

    private boolean modDebug(long i, long prev) {
        if (i % 1000 == 0) {
            System.out.print("Line " + i + " parsed at ");
            timestamp(prev);
            return true;
        }
        return false;
    }
}