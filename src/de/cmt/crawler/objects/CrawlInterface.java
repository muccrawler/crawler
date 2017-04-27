/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.objects;

/**
 *
 * @author cmt
 */
public interface CrawlInterface {   
    public int getId();
    
    public String getUrl();
    
    public double getPriority();
       
    public void setId(int id);
    
    public void setUrl(String url);
    
    public void setPriority(double priority);    
}
