/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.listener;

import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import tiennh.caketable.CakeTableDAO;
import tiennh.util.CakeHelper;

/**
 * Web application lifecycle listener.
 *
 * @author Will
 */
public class PriceRangeInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CakeTableDAO dao = new CakeTableDAO();
        try {
            List<Integer> range = dao.getPriceRange();
            CakeHelper.setPriceRange(range.get(1), range.get(0));

            List<Integer> rangeStock = dao.getPriceRangeInStock();
            CakeHelper.setPriceRangeInStock(rangeStock.get(1), rangeStock.get(0));
        } catch (Throwable t) {
            System.out.println("woops-" + this.getClass().getSimpleName());
            System.out.println(t.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
