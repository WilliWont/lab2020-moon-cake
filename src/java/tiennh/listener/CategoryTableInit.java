/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.listener;

import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import tiennh.categorytable.CategoryTableDAO;
import tiennh.categorytable.CategoryTableDTO;

/**
 * Web application lifecycle listener.
 *
 * @author Will
 */
public class CategoryTableInit implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            CategoryTableDAO cate = new CategoryTableDAO();
            List<CategoryTableDTO> cateList = cate.getAllCategory();
            sce.getServletContext().setAttribute("CATE_LIST", cateList);
        } catch (Throwable t) {
            // bad practice, fix later if have time
            System.out.println(t.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
