/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.objects.Host;

/**
 *
 * @author cmt
 */
public class HostFactory {
    
    public static Host get() {
        Host host = new Host();
        
        host.setHost("www.example.com");
        host.setPort(-1);
        host.setProtocol("http");
        
        return host;
    }
    
}
