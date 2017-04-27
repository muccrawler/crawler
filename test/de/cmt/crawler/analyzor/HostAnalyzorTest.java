/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.analyzor.HostAnalyzor;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cmt
 */
public class HostAnalyzorTest {
    
    private String url = "http://heise.de";
    
    private HostAnalyzor hostAnalyzor = null;
    
    public HostAnalyzorTest() {        
        try {
            this.hostAnalyzor = new HostAnalyzor(this.url);
        } catch (MalformedURLException ex) {
            fail("Valid URL should not fail");
        }
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
     * Test of getHost method, of class HostAnalyzor.
     */
    @Test
    public void testGetHost() {
        String expected = "www.cmt.de";        
        assertEquals(expected, hostAnalyzor.getHost());       
    }

    /**
     * Test of getPath method, of class HostAnalyzor.
     */
    @Test
    public void testGetPath() {
        String expected = "/IT-Trainings/Webprogrammierung-Webgestaltung/Java-Aufbaukurs-80.html";        
        assertEquals(expected, hostAnalyzor.getPath());    
    }

    /**
     * Test of getPort method, of class HostAnalyzor.
     */
    @Test
    public void testGetPort() {
        int expected = -1;
        
        assertEquals(expected, hostAnalyzor.getPort());
    }

    /**
     * Test of getProtocol method, of class HostAnalyzor.
     */
    @Test
    public void testGetProtocol() {
        String expected = "http";
        
        assertEquals(expected, hostAnalyzor.getProtocol());
    }

    /**
     * Test of getIp method, of class HostAnalyzor.
     * 
     * 
     */
    @Test
    public void testGetIp() throws Exception {
        String expected = "46.4.111.165";
        
        // Risky Test - Requires internet connection!!
        // assertEquals(expected, hostAnalyzor.getIp());
    }
   
}
