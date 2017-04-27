/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.Site;

/**
 *
 * @author cmt
 */
public class RatingAnalyzor 
{
    private Site site;
    private Host host;
       
    public RatingAnalyzor(Site site, Host host) {
        this.site = site;
        this.host = host;
    }
   
    public double getRating() {
        // TODO
        return 10;
    }
}
