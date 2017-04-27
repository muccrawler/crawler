/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cmt.crawler.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmt
 */
public class PluginHelper {
    
    private static PluginInterface[] plugins = null;
    
    public static PluginInterface[] getPlugins() throws MalformedURLException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        
        if (plugins == null)
        {
            ArrayList<PluginInterface> pluginsAl = new ArrayList<PluginInterface>();
            
            File pluginFolder = new File("plugins/");
            
            File[] pluginFiles = pluginFolder.listFiles();
            
            for (int i = 0; i < pluginFiles.length; i++) {
                File pluginFile = pluginFiles[i];
                
                String fileName = pluginFile.getName();
                
                if (!fileName.endsWith(".jar")) {
                    continue;
                }
                
                JarFile jar = new JarFile(pluginFile);
                
                Manifest manifest = jar.getManifest();
                
                ClassLoader cl = URLClassLoader.newInstance(new URL[] { pluginFile.toURL() });

                String pluginClass = manifest.getMainAttributes().getValue("Main-Class");
                
                PluginInterface plugin = (PluginInterface) cl.loadClass(pluginClass).newInstance();
                
                pluginsAl.add(plugin);
            }
            
            if (pluginsAl.isEmpty())
            {
                return null;
            }
            
            PluginHelper.plugins = new PluginInterface[pluginsAl.size()];
                       
            for (int i = 0; i < pluginsAl.size(); i++) {
                PluginHelper.plugins[i] = pluginsAl.get(i);
            }
        }
        
        // plugin.process(site, host, document);
        return PluginHelper.plugins;
    }
    
    public static void main(String[] args) {
        try {
            PluginHelper.getPlugins();
        } catch (InstantiationException ex) {
            Logger.getLogger(PluginHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PluginHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PluginHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PluginHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
