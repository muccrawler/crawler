/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.HostSqlHelper;
import de.cmt.crawler.db.SiteSqlHelper;
import de.cmt.crawler.db.SiteToCrawlSqlHelper;
import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.Site;
import de.cmt.crawler.objects.SiteToCrawl;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmt
 */
public class SiteCrawlerController extends CrawlerController {
    public SiteToCrawl siteToCrawl;
    
    public SiteCrawlerController(SiteToCrawl site) {
        this.siteToCrawl = site;
    }
    
    public void process() {
        Connection con = null;
        

        
        try {
            Host host = this.getHost(siteToCrawl);
            
            if (host == null) {
                this.deleteObject(siteToCrawl);
                return;
            }

            String path = siteToCrawl.getPath();            
           
            con = DbCon.get();
            
            int cnt = SiteSqlHelper.exists(con, host.getId(), path);            
            
            if (cnt > 0) {
                this.deleteObject(siteToCrawl);
                return;
            }            
            
            // Site
            SiteController sc = new SiteController(siteToCrawl, host);
            
            Site site = sc.process();
            
            // Save
            int siteId = SiteSqlHelper.create(con, site);
            
            site.setId(siteId);

            // Fertig mit dem Link
            this.deleteObject(siteToCrawl);          
        } catch (Exception ex) {          
            try {
                this.deleteObject(siteToCrawl);
            } catch (Exception e) {                   
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }
    }        
    
    public static void main(String[] args) {
        SiteToCrawl stc = new SiteToCrawl();
        stc.setId(1);
        stc.setPriority(10);
        stc.setUrl("http://heise.de");
        
        SiteCrawlerController cc = new SiteCrawlerController(stc);
        cc.process();
    }
}
