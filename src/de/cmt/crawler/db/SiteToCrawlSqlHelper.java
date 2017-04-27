/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.db;

import de.cmt.crawler.objects.ImageToCrawl;
import de.cmt.crawler.objects.Site;
import de.cmt.crawler.objects.SiteToCrawl;
import de.cmt.crawler.objects.ObjectToCrawl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class SiteToCrawlSqlHelper {

    /**
     * Creates a new link to crawl
     * 
     * @param con Database connection
     * @param site Site to crawl
     * @return true on success
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static boolean create(Connection con, ObjectToCrawl site) throws SQLException, ClassNotFoundException {       
        String query = "INSERT INTO " + site.getTable() + " (url, priority)"
                + " VALUES "
                + "(?, ?)";        
                        
        PreparedStatement ps = con.prepareStatement(query);        
        
        ps.setString(1, site.getUrl());
        ps.setDouble(2, site.getPriority());

        ps.execute();
        
        return true;
    }
    
    /**
     * Deletes an link
     * @param con DB Connection
     * @param site ObjectToCrawl
     * @return true on success
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static boolean delete(Connection con, ObjectToCrawl site) throws SQLException, ClassNotFoundException {        
        String query = "DELETE FROM " + site.getTable() + " WHERE id = ?";
        
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setInt(1, site.getId());
        
        ps.execute();
        
        return true;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {       
    }

    /**
     * Reads a list of links to crawl
     * @param con Db Connection
     * @param offset Start Offset
     * @param limit Limit
     * @return Array of Links
     * @throws SQLException
     * @throws ClassNotFoundException 
     */        
    public static ObjectToCrawl[] read(Connection con, String table, int offset, int limit) throws SQLException, ClassNotFoundException {
        ObjectToCrawl[] links = null;

        String query = "SELECT * FROM " + table + " ORDER BY priority DESC LIMIT ?, ?";
        
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setInt(1, offset);
        ps.setInt(2, limit);
        
        ps.execute();
        
        ResultSet set = ps.getResultSet();
        
        set.last();
        int cnt = set.getRow(); // 
        set.beforeFirst();
        
        if (cnt == 0) {
            return null;
        }
        
        if (table.equals(SiteToCrawl.TABLE)) {
            links = new SiteToCrawl[cnt];
        } else {
            links = new ImageToCrawl[cnt];
        }
        
        for (int i = 0; i < cnt; i++) {
            set.next();
            
            if (table.equals(SiteToCrawl.TABLE)) {
                links[i] = new SiteToCrawl();
            } else {
                links[i] = new ImageToCrawl();
            }
            
            links[i].setId(set.getInt("id"));
            links[i].setUrl(set.getString("url"));
            links[i].setPriority(set.getDouble("priority"));
        }
        
        return links;
    }
}
