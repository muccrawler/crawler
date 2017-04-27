/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmt
 */
public class NetworkThread extends Thread {
    
    private Socket socket;
    
    public NetworkThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try 
        {            
            System.out.println("Neue Verbindung von " + socket.getInetAddress());

            // Ausgabe zum Client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            // Eingabe vom Client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String inputText, outputText;
            
            CrawlerProtocol protocol = new CrawlerProtocol();
            
            // Willkommens Nachricht
            String msg = protocol.processInput(null);            
            out.println(msg);
            
            // Verbindung offen halten -> auf Client Eingaben reagieren
            while ((inputText = in.readLine()) != null) {
                outputText = protocol.processInput(inputText);
                out.println(outputText);
                
                if (inputText.equals("exit")) {
                    break;
                }
                
                if (outputText.equals("unauthorized")) {
                    break;
                }
            }

            System.out.println("Closing Socket");
            socket.close();            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.socket.close();
                NetworkService.threadCount--;
            } catch (IOException ex) {
                Logger.getLogger(NetworkThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
