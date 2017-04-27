/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.HostSqlHelper;
import de.cmt.crawler.db.ImageToCrawlSqlHelper;
import de.cmt.crawler.db.SiteToCrawlSqlHelper;
import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.ImageToCrawl;
import de.cmt.crawler.objects.ObjectToCrawl;
import de.cmt.crawler.objects.SiteToCrawl;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class CrawlerController {

    public Host getHost(ObjectToCrawl object) {

        Connection con = null;
        Host host = null;
       
        try {
            HostController hostController = new HostController(object);

            host = hostController.process();

            // Save
            con = DbCon.get();

            int hostId = HostSqlHelper.exists(con, host.getHost());

            if (hostId == 0) {
                // Create
                hostId = HostSqlHelper.create(con, host);
            }

            host.setId(hostId);

        } catch (Exception e) {
            return null;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                }
            }
        }

        return host;
    }
    
    protected void deleteObject(ObjectToCrawl object) throws SQLException, ClassNotFoundException {
        Connection con = null;
        
        try {
            con = DbCon.get();
            
            if (object instanceof SiteToCrawl) {
                SiteToCrawlSqlHelper.delete(con, object);
            } else if (object instanceof ImageToCrawl) {
                ImageToCrawlSqlHelper.delete(con, object);
            } else {
                System.out.println("ERROR unknown instance type");
            }
        } catch (Exception e) {            
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
