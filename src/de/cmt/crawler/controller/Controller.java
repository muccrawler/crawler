/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.CrawlerService;
import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.ImageToCrawlSqlHelper;
import de.cmt.crawler.db.SiteToCrawlSqlHelper;
import de.cmt.crawler.objects.ImageToCrawl;
import de.cmt.crawler.objects.ObjectToCrawl;
import de.cmt.crawler.objects.SiteToCrawl;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class Controller extends Thread {
    private int offset;
    
    private ObjectToCrawl[] entries = null;
    
    private int type;
    
    private static final int BREAKTIME = 5000;
    
    public static final int CRAWL_SITES = 0;
    public static final int CRAWL_IMAGES = 1;
        
    public Controller(int offset, int type) {
        this.offset = offset;
        this.type = type;
    }
    
    public void run() {      
        //  Stops the thread if Crawler Service is set to that
        if (CrawlerService.status == CrawlerService.STOPPED) {
            return;
        }
        
        try {
            this.loadEntries();
        } catch (Exception e) {
            this.restart();
            return;
        }
        
        if (entries == null) {
           this.restart();
           return;
        }
        
        for (int i = 0; i < entries.length; i++)
        {           
            if (this.type == CRAWL_SITES) {
                SiteCrawlerController cc = new SiteCrawlerController((SiteToCrawl) entries[i]);
                cc.process();
            } else {
                ImageCrawlerController cc = new ImageCrawlerController((ImageToCrawl) entries[i]);
                cc.process();
            }            
        }
        
        this.entries = null;
        
        Runtime.getRuntime().gc();
        
        this.run();
    }
    
    protected void loadEntries() throws SQLException, ClassNotFoundException {
        Connection con = null;
        
        try {
            con = DbCon.get();
            
            if (type == CRAWL_SITES) {
                this.entries = (SiteToCrawl[]) SiteToCrawlSqlHelper.read(con, SiteToCrawl.TABLE, this.offset, 100);
            } else {
                this.entries = (ImageToCrawl[]) ImageToCrawlSqlHelper.read(con, ImageToCrawl.TABLE, this.offset, 100);
            }
        } catch (Exception e) {
            this.entries = null;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    
    protected void restart() {
        try {
            System.out.println("Restarting Thread");
            sleep(BREAKTIME);
            this.run();
        } catch (InterruptedException ex) {
        }
    }
}
