/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.network;

import de.cmt.crawler.CrawlerConfig;
import de.cmt.crawler.CrawlerService;
import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.SiteToCrawlSqlHelper;
import de.cmt.crawler.objects.SiteToCrawl;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.validator.routines.UrlValidator;

/**
 *
 * @author cmt
 */
public class CrawlerProtocol {
    
    private static final int MAX_PRIORITY = 42;
    
    private static final int GREETING = 0;
    private static final int WAITING  = 1;
    private static final int SHUTDOWN = 2;
    private static final int AUTHENTICATING = 3;
    
    private int state = GREETING;
    
    private int errorCount = 0;
    
    public String processInput(String input)
    {
        if (input != null) {
            input = input.toLowerCase().trim();
        }
        
        String output = null;
        
        if (state == GREETING) {
            this.state = AUTHENTICATING;
            String welcome = "Welcome to Crawler Project\n\r";
            welcome += "Please enter the secret key:\n\r";         
            return welcome;
        }
        
        if (state == AUTHENTICATING) {
            String secret = CrawlerConfig.getInstance().getString("password");
            
            if (input.equals(secret)) {
                this.state = WAITING;
                return "Sucessfully logged in";
            } else {
                errorCount++;

                if (errorCount == 3) {
                    return "unauthorized";
                }                
            }                        
        }
        
        if (state == WAITING) {
            if (input.equals("help")) {
                return this.getHelp();
            }
                        
            if (input.equals("shutdown")) {
                this.state = SHUTDOWN;
                return "Are you sure? (y/N)\n";               
            }
            
            if (input.startsWith("addlink")) {
                boolean result = this.addLink(input);
                
                if (!result) {
                    return "Error creating Link, is the Link valid?\n";
                }
                
                return "Successfully added Link\n";
            }
            
            if (input.equals("status")) {
                return this.getStatus();
            }
            
            if (input.equals("start")) {
                return this.startCrawler();
            }
            
            if (input.equals("stop")) {
                return this.stopCrawler();
            }
        }        
        
        if (state == SHUTDOWN) {
            if (input.equals("y")) {
                this.shutdown();
                return "Shuting down\n";
            } else if (input.equals("n")) {
                this.state = WAITING;
                return "Aborted shutdown\n";
            }
        }
        
        return "Unknown Command\n";
    }
    
    public String getHelp() {
        String helpText = "\nThe following commands are available:\n\r";
        
        helpText += "start\t\tStarts the server\n\r";
        helpText += "stop\t\tStops the server\n\r";
        helpText += "shutdown\tShut down the crawler\n\r";
        helpText += "status\t\tShows the status of the server\n\r";
        helpText += "addlink {URL}\tAdds the link to the crawl list with highest priority\n\n\r";
        
        return helpText;
    }
    
    public void shutdown() {
        System.exit(0);
    }
    
    public boolean addLink(String input) {
        String url = input.replaceFirst("addlink", "").trim();

        UrlValidator uv = new UrlValidator();

        if (!uv.isValid(url)) {
            return false;
        }
        
        
        Connection con = null;
        
        try 
        {                    
            con = DbCon.get();
            SiteToCrawl link = new SiteToCrawl();

            link.setUrl(url);
            link.setPriority(MAX_PRIORITY);

            SiteToCrawlSqlHelper.create(con, link);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CrawlerProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return true;
    }
    
    public String getStatus() {
        if (CrawlerService.status == CrawlerService.RUNNING) {
            return "Crawler is working\n";
        } 
        
        return "Crawler is stopped\n";
    }
    
    public String startCrawler() {
        if (CrawlerService.status == CrawlerService.RUNNING) {
            return "Crawler is already running\n";
        }
        
        CrawlerService.startCrawler();
        
        return "Crawler has been started\n";
    }
    
    public String stopCrawler() {
        if (CrawlerService.status == CrawlerService.STOPPED) {
            return "Crawler is already stopped\n";
        }
        
        CrawlerService.stopServer();
        
        return "Crawler is stopping, please not that can take some seconds\n";
    }
}
