/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.analyzor.HostAnalyzor;
import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.ObjectToCrawl;
import de.cmt.crawler.objects.SiteToCrawl;
import java.net.MalformedURLException;

/**
 *
 * @author cmt
 */
public class HostController {
    
    private Host host;
    
    private ObjectToCrawl siteToCrawl;
    
    /**
     * Crawl a host
     * 
     * @param site Link to crawl
     */
    public HostController(ObjectToCrawl site) {
        this.siteToCrawl = site;
        
        this.host = new Host();
    }
    
    /**
     * Process the host
     * 
     * @return
     * @throws MalformedURLException 
     */
    public Host process() throws MalformedURLException {
        System.out.println("Crawling " + this.siteToCrawl.getUrl());
        HostAnalyzor ha = new HostAnalyzor(this.siteToCrawl.getUrl());
        
        this.host.setHost(ha.getHost());
        this.host.setPort(ha.getPort());
        this.host.setProtocol(ha.getProtocol());
   
        return this.host;
    }
}
