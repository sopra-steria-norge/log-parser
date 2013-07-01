/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.osl.cdms.profile.utilities;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nutgaard
 */
public class GuavaHelpersTest {
    
    public GuavaHelpersTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isDuration method, of class GuavaHelpers.
     */
    @Test
    public void testIsDuration() {
        System.out.println("isDuration");
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("key", "value");
        map.put("key.duration", "value");
        boolean[] expResult = {false, true};
        
        Set<Map.Entry<String, String>> entries = map.entrySet();
        int i = 0;
        for (Entry<String, String> entry : entries){
            assertEquals(expResult[i++], GuavaHelpers.isDuration().apply(entry));   
        }
    }

    /**
     * Test of getConverter method, of class GuavaHelpers.
     */
    @Test
    public void testGetConverter() {
        System.out.println("getConverter");
        Map<String, String> properties = null;
        Function expResult = null;
        Function result = GuavaHelpers.getConverter(properties);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}