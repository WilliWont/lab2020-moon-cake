/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.listener;

import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Will
 */
public class AsgnServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("--AsgnServletListener--");
        ServletContext ctx = sce.getServletContext();
        try {
            ResourceBundle siteMap = ResourceBundle.getBundle("tiennh.properties.sitemap");
            ctx.setAttribute("SITE_MAP", siteMap);
        } catch (Throwable t) {
            ctx.log("AsginServletListener-" + t.getMessage());
        } finally {
            System.out.println("-----------------------");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
