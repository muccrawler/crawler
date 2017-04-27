/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.analyzor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author cmt
 */
public class HtmlAnalyzor {
    
    private String html;
    
    private Document doc;
        
    /**
     * Constructor for Html
     * @param html The document html String
     */
    public HtmlAnalyzor(String html, Document doc) {
        this.html = html;
        this.doc  = doc;
    }
    
    /**
     * Get Links for this doc
     * @return Links Array
     */
    public String[] getLinks() {               
        return this.getData("a", "href");
    }
    
    public String[] getImages() {       
        return this.getData("img", "src");
    }
    
    /**
     * Get the Elements attribute value
     * 
     * @param selector Element selector (like a, img etc.)
     * @param attribute Which attribute to get
     * @return Array of Strings
     */
    protected String[] getData(String selector, String attribute) {
        Elements elements = this.doc.select(selector);
        
        Object[] objects = elements.toArray();
        String[] items   = new String[objects.length];
        
        for (int i = 0; i < objects.length; i++) {
            Element element = (Element) objects[i];
            items[i] = element.attr(attribute);            
        }
        
        return items;
    }
    
    /**
     * Get the description
     * @return Description
     */
    public String getMetaDescription() {
       return this.getElementContentAttribute("meta[name=description]");
    }
    
    /**
     * Get the Meta Keywords for this document
     * @return Keywords or empty string
     */
    public String getMetaKeywords() {
        return this.getElementContentAttribute("meta[name=keywords]");
    }
    
    /**
     * Intern method for getting an content attribute of an element
     * 
     * @param key
     * @return Attribute or empty string
     */
    protected String getElementContentAttribute(String key) {
        Elements elements = this.doc.select(key);
        
        Object[] objects = elements.toArray();
        String result = "";

        // Eigentlich unnötig - da nur max. 1 Element (normalerweise .. ?)
        for (int i = 0; i < objects.length; i++) {
            Element element = (Element) objects[i];
            result = element.attr("content");
        }
        
        return result;
    }
    /**
     * Get the title of the document
     * 
     * @return Title
     */
    public String getTitle() {
        return this.doc.title();
    }
}
