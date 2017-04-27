/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmt
 */
public class CrawlerConfig {

    private Properties config = null;
    
    private static CrawlerConfig instance = null;

    /**
     * Singleton class
     *
     * Only instantation through get instance
     */
    private CrawlerConfig()  {
        // Load configuration once
        this.config = this.loadConfig();
    }

    public static CrawlerConfig getInstance()  {
        // Nur eine Instanz eines Objects möglich
        if (instance == null) {
            instance = new CrawlerConfig();
        }

        return instance;
    }

    protected Properties loadConfig()  {
        File configFile = new File("config.ini");

        Properties pr = new Properties();

        try {
            pr.load(new FileReader(configFile));
        } catch (IOException ex) {
            Logger.getLogger(CrawlerConfig.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        
        return pr;
    }
    
    public Object get(String key) {
        return this.config.get(key);
    }

    public String getString(String key) {
        return String.valueOf(this.config.get(key)).trim();
    }
}
