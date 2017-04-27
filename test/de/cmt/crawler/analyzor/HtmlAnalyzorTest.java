package de.cmt.crawler.analyzor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import de.cmt.crawler.analyzor.HtmlAnalyzor;
import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cmt
 */
public class HtmlAnalyzorTest {
    
    private static final String html = "<html>"
                + "<head>"
                + "<title>Test Title</title>"
                + "<meta name=\"description\" content=\"Test Description\">"
                + "<meta name=\"keywords\" content=\"test, cmt, Feierabend\">"
                + "<link href=\"style.css\" rel=\"stylesheet\">"
                + "</head>"
                + "<body>"
                + "<h3>Test Seite</h3>"
                + "<a href=\"heise.de\" target=\"_blank\">heise.de</a>"
                + "<a href=\"golem.de\" target=\"_blank\">heise.de</a>"
                + "<a href=\"unterseite.html\">Unterseite</a>"
                + "<a href=\"test22.de\">test22.de</a>"
                + "<a href=\"verzeichnis/verzeichnis/unter.html\">Unter Unter</a>"
                + "<a title=\"irgendwas\" href=\"blabla.de\">blabla.de</a>"
                + "</body>"
                + "</html>";
    
    public HtmlAnalyzorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetLinks() {
        System.out.println("testGetLinks");
        
        String[] links = {
            "heise.de",
            "golem.de",
            "unterseite.html",
            "test22.de",
            "verzeichnis/verzeichnis/unter.html",
            "blabla.de"
        };
                
        HtmlAnalyzor ha = new HtmlAnalyzor(HtmlAnalyzorTest.html, Jsoup.parse(HtmlAnalyzorTest.html));
        String[] result = ha.getLinks();
               
        assertArrayEquals(links, result);
    }
    
    @Test
    public void testGetMetaDescription() {
        System.out.println("testGetDescription");
        HtmlAnalyzor ha = new HtmlAnalyzor(HtmlAnalyzorTest.html, Jsoup.parse(HtmlAnalyzorTest.html));
        
        String expected = "Test Description";
        
        String description = ha.getMetaDescription();
        
        assertEquals(expected, description);
    }
    
    @Test
    public void testGetKeywords() {
        System.out.println("testGetKeyWords");
        HtmlAnalyzor ha = new HtmlAnalyzor(HtmlAnalyzorTest.html, Jsoup.parse(HtmlAnalyzorTest.html));
        
        String expected = "test, cmt, Feierabend";
        
        String keywords = ha.getMetaKeywords();
        
        assertEquals(expected, keywords);
    }
    
    @Test
    public void testGetTitle() {
        System.out.println("testGetTitle");
        
        HtmlAnalyzor ha = new HtmlAnalyzor(HtmlAnalyzorTest.html, Jsoup.parse(HtmlAnalyzorTest.html));
        
        String expected = "Test Title";
        
        assertEquals(expected, ha.getTitle());
    }
}
