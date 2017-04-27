/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler;

import de.cmt.crawler.network.NetworkService;
import de.cmt.crawler.controller.Controller;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author cmt
 */
public class CrawlerRun {
           
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       // Crawler starten
       CrawlerService.startCrawler();
        
       // Netzwerk Service starten
       NetworkService.start();
    }   
}
