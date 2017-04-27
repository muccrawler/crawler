/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class DbCon {
    
    public static Connection get() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
                
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/crawler", "root", "");
    }    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection con = DbCon.get();
    }
}
