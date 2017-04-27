/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.db;

import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.Site;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class HostSqlHelper {
    public static int create(Connection con, Host host) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO hosts (host, port, protocol)"
                + " VALUES "
                + "(?, ?, ?)";
        
        PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        
        ps.setString(1, host.getHost());
        ps.setInt(2, host.getPort());
        ps.setString(3, host.getProtocol());

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
    
        
    public static int exists(Connection con, String host) throws SQLException, ClassNotFoundException {       
        String query = "SELECT id FROM hosts WHERE host = ?";               
        
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setString(1, host);
        
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        
        if (rs.next()) {
            return rs.getInt(1);
        }
        
        return 0;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Host host = new Host();
        
//        host.setHost("www.cmt.de");
//        host.setPort(-1);
//        host.setProtocol("http");
        
        // int result = HostSqlHelper.create(host);
        
//        int result = HostSqlHelper.exists("www.cmt.de");
        
       // System.out.println("result: " + result);
    }
}
