/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler;

import de.cmt.crawler.controller.Controller;

/**
 *
 * @author cmt
 */
public class CrawlerService {

    public static final String OS = System.getProperty("os.name");

    public static final int STOPPED = 0;
    public static final int RUNNING = 1;
    
    public static int status = STOPPED;
    
    public static Controller[] siteThreads = null;
    public static Controller[] imageThreads = null;

    public static boolean startCrawler() {
        CrawlerService.status = CrawlerService.RUNNING;      
        
        int cpuCores = Runtime.getRuntime().availableProcessors();

        int imageCores = (int) Math.round(cpuCores * 0.25);

        imageCores = imageCores == 0 ? 1 : imageCores;
        int siteCores = cpuCores - imageCores;

        // Array von Controllern für das Site Crawling mit der Anzahl der Kerne       
        CrawlerService.siteThreads = new Controller[siteCores];

        for (int i = 0; i < siteCores; i++) {
            int offset = i * 200;

            CrawlerService.siteThreads[i] = new Controller(offset, Controller.CRAWL_SITES);
            CrawlerService.siteThreads[i].start();
        }

        CrawlerService.imageThreads = new Controller[imageCores];

        for (int i = 0; i < imageCores; i++) {
            int offset = i * 200;

            CrawlerService.imageThreads[i] = new Controller(offset, Controller.CRAWL_IMAGES);
            CrawlerService.imageThreads[i].start();
        }
        
        
        return true;
    }
    
    /**
     * Stop Server
     */
    public static void stopServer() {
         CrawlerService.status = CrawlerService.STOPPED;
    }
}
