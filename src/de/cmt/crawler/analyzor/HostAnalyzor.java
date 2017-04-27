/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 *
 * @author cmt
 */
public class HostAnalyzor {
    
    /**
     * The URL to analyze
     */
    private URL url = null;
    
    /**
     * Creates a new Host analyzor (REQUIRES full absoute URL)
     * 
     * @param url Absolute URL (http://www.example.com/test42.html)
     * @throws MalformedURLException 
     */
    public HostAnalyzor(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
    
    /**
     * Get the host
     * 
     * @return Host of the address
     */
    public String getHost() {
        return this.url.getHost();
    }
    
    /**
     * Path of the URL
     * 
     * @return Path
     */
    public String getPath() {
        return this.url.getPath();
    }
    
    /**
     * Get the Port
     * 
     * @return 
     */
    public int getPort() {
        return this.url.getPort();
    }
    
    /**
     * Get the protocol
     * 
     * @return 
     */
    public String getProtocol() {
        return this.url.getProtocol();
    }
    
    /**
     * Requires internet connection!
     * 
     * @return
     * @throws UnknownHostException 
     */
    public String getIp() throws UnknownHostException {
        return InetAddress.getByName(this.getHost()).getHostAddress();
    }
}


