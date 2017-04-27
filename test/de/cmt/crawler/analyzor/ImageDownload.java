/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author cmt
 */
public class ImageDownload {
    public static void main(String[] args) throws MalformedURLException, IOException {
        URL url = new URL("http://www.cmt.de/gfx/Logos/cmt-web.png");
        
        InputStream is = url.openStream();
        
        BufferedInputStream bis = new BufferedInputStream(is);
        
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File("test.png")));
        
        int byteTemp = 0;
        
        byte[] buffer = new byte[1024];
        
        while (bis.read(buffer) != -1) {
            os.write(buffer);
        }
        
        os.close();
        bis.close();
        
        Image image = ImageIO.read(url);
        
        
        // ImageIO.write(image, , output)
    }
}
