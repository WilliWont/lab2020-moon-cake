/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.caketable.CakeTableDTO;
import tiennh.cart.CartObject;

/**
 *
 * @author Will
 */
public class RemoveItemServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCart";

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
        PrintWriter out = response.getWriter();
        String txtCakeID = request.getParameter("txtCakeID");
        try {
            HttpSession ses = request.getSession();
            CartObject cart = (CartObject) ses.getAttribute("SES_CART");
            if (txtCakeID != null) {
                if (cart != null) {
                    List<CakeTableDTO> products = cart.getProductList();
                    if (products != null && !products.isEmpty()) {
                        int itemId;
                        try {
                            itemId = Integer.parseInt(txtCakeID);
                            cart.removeItem(itemId);
                        } catch (Exception e) {
                            log(this.getClass()
                                    .getSimpleName() + '-' + e.getMessage());
                        }
                        ses.setAttribute("SES_CART", cart);
                    }
                }
            }
        } catch (Throwable t) {
            log("RemoveItemServlet-" + t.getMessage());
        } finally {
            response.sendRedirect(VIEW_CART_PAGE);

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
