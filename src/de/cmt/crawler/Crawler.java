/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler;

import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.ImageToCrawlSqlHelper;
import de.cmt.crawler.objects.ImageToCrawl;
import java.sql.SQLException;

/**
 *
 * @author cmt
 */
public class Crawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        
        ImageToCrawl test = new ImageToCrawl();
        
        test.setUrl("http://test.de/images/img.jpg");
        test.setPriority(10);
        
        try {
            ImageToCrawlSqlHelper.create(DbCon.get(), test);
        } catch (Exception e) {            
        }       
        
        
    }
    
}
