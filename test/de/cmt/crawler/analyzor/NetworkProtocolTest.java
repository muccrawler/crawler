/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import java.net.URI;
import java.net.URL;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.commons.validator.util.ValidatorUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cmt
 */
public class NetworkProtocolTest {
    @Test
    public void testUrlValidation() {
        String test = "http:/7ww//www.cmt.de";      
        
        boolean hadException = false;
       
        UrlValidator uv = new UrlValidator();
                   
        assertEquals(false, uv.isValid(test));
    }
}
