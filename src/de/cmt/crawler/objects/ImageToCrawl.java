/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.objects;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Images zu crawlen
 * 
 * @author cmt
 */
public class ImageToCrawl extends SiteToCrawl
{
    public static final String TABLE = "images_to_crawl";  
    
    public String getTable() {
        return "images_to_crawl";
    }
}
