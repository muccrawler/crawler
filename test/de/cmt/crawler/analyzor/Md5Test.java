/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import de.cmt.crawler.db.DbCon;
import de.cmt.crawler.db.ImageToCrawlSqlHelper;
import de.cmt.crawler.objects.ImageToCrawl;
import de.cmt.crawler.objects.ObjectToCrawl;
import de.cmt.crawler.objects.SiteToCrawl;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author cmt
 */
public class Md5Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, ClassNotFoundException {
        
        SiteToCrawl[] entries2 =
                (SiteToCrawl[]) ImageToCrawlSqlHelper.read(DbCon.get(), SiteToCrawl.TABLE, 0, 100);
       
        
        ImageToCrawl[] entries =
                (ImageToCrawl[]) ImageToCrawlSqlHelper.read(DbCon.get(), ImageToCrawl.TABLE, 0, 100);
       
        System.out.println("length " + entries.length);
        System.out.println("length " + entries2.length);

    }
}
