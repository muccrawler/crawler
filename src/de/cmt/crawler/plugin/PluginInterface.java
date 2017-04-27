/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.plugin;

import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.SiteToCrawl;
import org.jsoup.nodes.Document;

/**
 *
 * @author cmt
 */
public interface PluginInterface {
    
    public void process(SiteToCrawl site, Host host, Document document);
    
}
