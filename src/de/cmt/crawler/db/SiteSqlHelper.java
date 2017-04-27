/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.db;

import de.cmt.crawler.objects.Site;
import de.cmt.crawler.objects.SiteToCrawl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class SiteSqlHelper {

    /**
     * Create a site entry
     * 
     * @param con  JDBC connection
     * @param site Site Object
     * @return id generated
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static int create(Connection con, Site site) throws SQLException, ClassNotFoundException {        
        String query = "INSERT INTO sites (host_id, title, metaDescription, metakeywords, path, rating)"
                + " VALUES "
                + "(?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        
        ps.setInt(1, site.getHostId());
        ps.setString(2, site.getTitle());
        ps.setString(3, site.getMetaDescription());
        ps.setString(4, site.getMetaKeywords());
        ps.setString(5, site.getPath());
        ps.setDouble(6, site.getRating());
                      
        ps.execute();
               
        ResultSet set = ps.getGeneratedKeys();
        
        int id = -1;
        
        if (set.next())
        {
            id = set.getInt(1);
        }

        set.close();
        
        return id;
    }
    
    /**
     * Checks if an site is existing
     * @param con DB Connection
     * @param host_id Host id
     * @param path Path to the site
     * @return id of the site if existing, else 0
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    
    public static int exists(Connection con, int host_id, String path) throws SQLException, ClassNotFoundException {       
        String query = "SELECT id FROM sites WHERE host_id = ? AND path = ?";               
        
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setInt(1, host_id);
        ps.setString(2, path);
        
        ps.execute();                
        
        ResultSet rs = ps.getResultSet();
        
        if (rs.next()) {
            return rs.getInt(1);
        }
        
        return 0;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Site site = new Site();
        
        site.setHostId(42);
        site.setMetaDescription("Test Desc");
        site.setMetaKeywords("test, keywords");
        site.setTitle("Eine schöne Seite");
        site.setPath("/");
        site.setRating(42.42);
        
        // int result = SiteSqlHelper.create(site);
        
        //System.out.println("result: " + result);
    }
}
