/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.objects;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Links zu crawlen
 * 
 * @author cmt
 */
public class SiteToCrawl extends ObjectToCrawl
{
    public static final String TABLE = "sites_to_crawl";
    
    private int id;
    
    private String url;
      
    private double priority;
    
    public SiteToCrawl() {       
    }
    
    public SiteToCrawl(String url) {
        this.url = url;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }
    
    /**
     * Get the path
     * @return the path of the url
     * @throws MalformedURLException 
     */
    public String getPath() throws MalformedURLException {
        URL url = new URL(this.url);
        
        if (url.getPath().equals("")) {
            return "/";
        }
        
        return url.getPath();
    }
    
    public String getTable() {
        return TABLE;
    }
}
