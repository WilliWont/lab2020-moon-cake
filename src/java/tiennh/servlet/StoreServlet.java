/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.category.CategoryObject;
import tiennh.usertable.UserTableDTO;
import tiennh.util.AccountHelper;
import tiennh.util.CakeHelper;
import tiennh.util.CategoryHelper;

/**
 *
 * @author Will
 */
public class StoreServlet extends HttpServlet {

    private final String STORE_PAGE = "storePage";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("at-" + this.getClass().getSimpleName());
        PrintWriter out = response.getWriter();
        ServletContext ctx = request.getServletContext();
        ResourceBundle siteMap = (ResourceBundle) ctx.getAttribute("SITE_MAP");
        HttpSession ses = request.getSession();
        UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");

        String url = siteMap.getString(STORE_PAGE);
        try {

            CakeTableDAO cakeDAO = new CakeTableDAO();
            boolean isAdmin
                    = (user != null
                    && user.getRole() == AccountHelper.ADMIN_ROLE);
//            List<CakeTableDTO> productList = cakeDAO.getAllProduct(false);
            List<CakeTableDTO> productList = cakeDAO
                    .searchProduct("", (float) CakeHelper.MIN_PRICE_RANGE,
                            (float) CakeHelper.MAX_PRICE_RANGE, null, isAdmin);

            CategoryHelper helper = new CategoryHelper();
            List<CategoryObject> cateList = helper.categorizeCakes(productList);

            request.setAttribute("PRODUCT_LIST", cateList);

        } catch (Throwable t) {
            log("StoreServlet-" + t);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
