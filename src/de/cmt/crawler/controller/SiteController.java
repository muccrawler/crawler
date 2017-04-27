/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.analyzor.HostAnalyzor;
import de.cmt.crawler.analyzor.HtmlAnalyzor;
import de.cmt.crawler.analyzor.LinkAnalyzor;
import de.cmt.crawler.analyzor.RatingAnalyzor;
import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.ImageToCrawlSqlHelper;
import de.cmt.crawler.db.SiteToCrawlSqlHelper;
import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.ImageToCrawl;
import de.cmt.crawler.objects.Site;
import de.cmt.crawler.objects.SiteToCrawl;
import de.cmt.crawler.plugin.PluginHelper;
import de.cmt.crawler.plugin.PluginInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author cmt
 */
public class SiteController {
    
    private SiteToCrawl siteToCrawl = null;
    
    private Site site = null;

    private Host host;
    
    /**
     * Site to analyze
     * 
     * @param site
     * @param host 
     */
    public SiteController(SiteToCrawl site, Host host) {
        // Eingabe
        this.siteToCrawl = site;
        
        // Ziel
        this.site = new Site();
        
        // TODO
        this.host = host;
    }
    
    /**
     * Process the site
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    public Site process() throws MalformedURLException, IOException {
        HostAnalyzor hostAnalyzor = new HostAnalyzor(this.siteToCrawl.getUrl());              
        
        // Analyze Host        
        URL url = new URL(this.siteToCrawl.getUrl());
        
        URLConnection con = url.openConnection();
               
        String status = con.getHeaderField(0);
        
        if (status == null) {
            return null;
        }                
                
        if (!status.contains("200 OK")) {
            if (status.contains("301 Moved Permanently"))
            {
                String newUrl = con.getHeaderField("Location");
                
                this.siteToCrawl.setUrl(newUrl);

                url = new URL(newUrl);
                
                con = url.openConnection();
            }
            else
            {
                return null;
            }
        }
        
        String type = con.getContentType();
        
        if (!type.contains("text/html"))
        {
            return null;
        }
              
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String zeile = "";
        
        StringBuilder sb = new StringBuilder();
        
        while ((zeile = br.readLine()) != null) {
            sb.append(zeile);           
        }
        
        br.close();
        
        String html = sb.toString();            
        Document doc = Jsoup.parse(html);
        
        // Plugins -> SiteToCrawl (link), Host, doc ?
        
        try {
            PluginInterface[] plugins = PluginHelper.getPlugins();
            
            for (PluginInterface plugin : plugins) {
                plugin.process(siteToCrawl, host, doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        HtmlAnalyzor htmlAnalyzor = new HtmlAnalyzor(html, doc);
        
        RatingAnalyzor rating = new RatingAnalyzor(this.site, this.host);
        
        this.site.setRating(rating.getRating());
        
        String[] newLink = htmlAnalyzor.getLinks();       
        this.saveNewLink(newLink);
        
        String[] newImages = htmlAnalyzor.getImages(); 
        System.out.println("Count Bilder " + newImages.length);
        
        this.saveNewImages(newImages);
        
        this.site.setTitle(htmlAnalyzor.getTitle());
        this.site.setMetaDescription(htmlAnalyzor.getMetaDescription());
        this.site.setMetaKeywords(htmlAnalyzor.getMetaKeywords());
        
        String path = hostAnalyzor.getPath();
        
        this.site.setPath(path == "" ? "/" : path);
        
        
        
        // Verknüpfung site <-> host
        this.site.setHostId(this.host.getId());
        
        return this.site;
    }    
    
    /**
     * Save a new link
     * 
     * @param newLink 
     */
    protected void saveNewLink(String[] newLink) {
        Connection con = null;
        
        for (int i = 0; i < newLink.length; i++) {
            try {
                String link = newLink[i];
                
                LinkAnalyzor la = new LinkAnalyzor(link, this.host);
                
                link = la.process();
                
                if (link == null) {
                    continue;
                }
                
                SiteToCrawl stc = new SiteToCrawl();
                stc.setUrl(link);
                
                int length = link.length();
                int prio = (int) Math.round(site.getRating() / 2);                              
                
                if (length < 10) {
                    prio += 5;
                } else if (length < 20) {
                    prio += 4;
                } else if (length < 30) {
                    prio += 3;
                } else if (length < 40) {
                    prio += 2;
                }
                
                stc.setPriority(prio);
                
                // Datenbank Verbindung
                con = DbCon.get();
                               
                SiteToCrawlSqlHelper.create(con, stc);
            } catch (Exception e) {
                // Egal               
            } finally {
                try {
                    con.close();
                } catch (Exception ex) {
                }
            }
        }
    }
    
    protected void saveNewImages(String[] images) {
        Connection con = null;
        
        System.out.println("Found images " + images.length); 
         
        for (int i = 0; i < images.length; i++) {
            try {
                String image = images[i];
                
                LinkAnalyzor la = new LinkAnalyzor(image, this.host);
                
                image = la.process();
                            
                if (image == null) {
                    continue;
                }
                
                // Check if its an valid image
                if (!la.isValidImage()) {
                    continue;
                }           
                
                int prio = 0;
                
                if (la.getExtension().equals("jpg") || "jpeg".equals(la.getExtension())) {
                    prio = 10;
                } else if ("gif".equals(la.getExtension())) {
                    prio = 7;
                } else if ("png".equals(la.getExtension())) {
                    prio = 7;
                } else if ("bmp".equals(la.getExtension())) {
                    prio = 3;
                }
                
                ImageToCrawl itc = new ImageToCrawl();
                
                itc.setUrl(image);
                itc.setPriority(prio);

                con = DbCon.get();
                
                ImageToCrawlSqlHelper.create(con, itc);                
            } catch (Exception e) {
                // Ignore
            } finally {
                if (con != null) {
                    try {
                    con.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }
          
    
    public static void main(String[] args) throws IOException {
        SiteController sc = new SiteController(new SiteToCrawl("http://heise.de"), new Host());
        
        System.out.println("Result: " + sc.process());
    }
}
