/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.objects.Host;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author cmt
 */
public class LinkAnalyzorTest {
    
    @Test
    public void isAbsolute() {
        Host host = HostFactory.get();
        
        LinkAnalyzor la = new LinkAnalyzor("http://www.example.com", host);
        
        assertTrue(la.isAbsolute());
        
        la = new LinkAnalyzor("test/test.html", host);
        
        assertFalse(la.isAbsolute());
    }
    
    @Test
    public void testIsValidProtocol() {
        System.out.println("testIsValidProtocol");
        
        Host host = HostFactory.get();
        
        LinkAnalyzor la = new LinkAnalyzor("http://www.example.com", host);
        
        assertTrue(la.isValidProtocol());
        
        la = new LinkAnalyzor("irc://www.example.com", host);
        
        assertFalse(la.isValidProtocol());
        
        la = new LinkAnalyzor("test22/test.html", host);
        
        assertTrue(la.isValidProtocol());
    }
    
    @Test
    public void testIsValidImage() {
        System.out.println("testIsValidImage");
        
        Host host = HostFactory.get();
        
        LinkAnalyzor la = new LinkAnalyzor("images/testimg.jpg", host);
        
        assertTrue(la.isValidImage()); 
                
        la = new LinkAnalyzor("images/testimgjpg.php", host);
        
        assertFalse(la.isValidImage()); 
        
        la = new LinkAnalyzor("images/testimg.jpg.php", host);
        
        assertFalse(la.isValidImage());
    }    
}
