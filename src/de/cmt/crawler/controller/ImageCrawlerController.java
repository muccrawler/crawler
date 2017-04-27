/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.controller;

import de.cmt.crawler.objects.Host;
import de.cmt.crawler.objects.ImageToCrawl;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author cmt
 */
public class ImageCrawlerController extends CrawlerController {
    
    private ImageToCrawl image;
    
    public ImageCrawlerController(ImageToCrawl image) {
        this.image = image;
    }
       
    /**
     * Process the image and download it
     */
    public void process() {
        System.out.println("Downloading image " + image);
        
        Host host = this.getHost(image);
            
        Connection con = null;
        
        try {
            if (host == null) {
                this.deleteObject(image);
                return;
            }
            
            // Download image
            String url = image.getUrl();
            
            URL imageUrl = new URL(url);
            BufferedImage imageObj = ImageIO.read(imageUrl);

            String originalFilename = imageUrl.getFile();                       
            String filename  = "" + DigestUtils.md5Hex(originalFilename + System.currentTimeMillis());                                   
            String results[] = originalFilename.split("\\.");            
            String extension = results[results.length - 1];
            
            File outputFile = new File("images/" + filename + "." + extension);
                                    
            ImageIO.write(imageObj, extension, outputFile);            
            
            this.deleteObject(image);
        } catch (Exception e) {            
            try {
                this.deleteObject(image);
            } catch (Exception ex) {                
            }
        } finally {
            if (con != null) {
                 try {
                    con.close();
                } catch (Exception ex) {                
                }                                
            }
        }
    }
}
