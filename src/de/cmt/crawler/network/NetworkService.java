/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmt
 */
public class NetworkService {
    
    public static int threadCount = 0;
    
    /**
     * Starts the network service
     */
    public static void start() {
        try {
            ServerSocket srv = new ServerSocket(4242);
            
            while (true) {
                Socket s = srv.accept();
                
                if (threadCount >= 5) {
                    System.out.println("Bitte Pro Lizenz erwerben");
                    continue;
                }
                
                // Pro Verbindung ein Thread
                NetworkThread nt = new NetworkThread(s);
                nt.start();
                
                threadCount++;
            }            
        } catch (IOException ex) {
            Logger.getLogger(NetworkService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
