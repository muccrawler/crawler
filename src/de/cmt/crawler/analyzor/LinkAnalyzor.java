/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.objects.Host;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.validator.routines.UrlValidator;

/**
 *
 * @author cmt
 */
public class LinkAnalyzor {
    
    /**
     * Link to analyze
     */
    private String link;
    
    /**
     * Host to look at
     */
    private Host host;
    
    public static final String[] VALID_EXTENSIONS = {"jpg", "gif", "png", "jpeg", "bmp", "img"};
    
    /**
     * Analyze a Link and try to validate it
     * 
     * @param link
     * @param host 
     */
    public LinkAnalyzor(String link, Host host) {
        // test.html
        // www.cmt.de/test.html
        this.link = link;
        this.host = host;
    }
    
    /**
     * Method to analyze and refactor a link to a valid one
     * 
     * @return 
     */
    public String process() {      
        // Hase the URL a valid protocol?
        if (!this.isValidProtocol()) {
            return null;
        }
               
        // Is the URL absolute with protocol? If not let's try to make one
        if (!this.isAbsolute()) {
            String hostString = this.host.getProtocol() + "://" + this.host.getHost();
            
            if (this.host.getPort() > 0) {
                hostString += ":" + this.host.getPort();
            }
                    
            this.link = hostString + this.link;
        }

        // Too many russian links
        if (this.host.getHost().contains(".ru") 
                || this.host.getHost().contains(".by")
                || this.host.getHost().contains(".kz")
                || this.host.getHost().contains(".pl")
                ) {
            return null;
        }
        
        UrlValidator uv = new UrlValidator();

        if (!uv.isValid(this.link)) {
            return null;
        }
        
        return this.link;
    }
    
    /**
     * Check if the URL contains a valid URL
     * 
     * @return boolean true if valid
     */
    public boolean isValidProtocol() {
        if (this.link.contains("://") && !(this.link.contains("http://")
                || this.link.contains("https://") )) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Is the URL absolute or not?
     * 
     * @return true if is absolute
     */
    public boolean isAbsolute() {
        if (this.link.contains("http://") || this.link.contains("https://")) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if an link is an valid image
     * 
     * @return true if valid
     */
    public boolean isValidImage() {                       
        String extension = getExtension();
        
        boolean isValidExtension = ArrayUtils.contains(VALID_EXTENSIONS, extension);
        
        return isValidExtension;
    }
    
    public String getExtension() {
        String[] parts = this.link.split("\\.");
        
        String extension = parts[parts.length - 1];
        
        return extension;
    }
}
