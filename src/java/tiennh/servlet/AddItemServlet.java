/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.cart.CartObject;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.util.DBHelper;

/**
 *
 * @author Will
 */
public class AddItemServlet extends HttpServlet {

    private final String ERROR_RESULT = "error";
    private final String SUCCESS_RESULT = "success";

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
        String productID = DBHelper.filterHTML(request.getParameter("txtCakeID"));
        Integer cakeID;
        try {
            cakeID = Integer.parseInt(productID);
        } catch (Throwable t) {
            cakeID = null;
        }
        System.out.println("received: " + productID);
        HttpSession ses = request.getSession();

        JsonObject toReturn = new JsonObject();
        Boolean hasError = true;
        String msg = "there was an error adding to cart";
        try {
            if (cakeID != null) {
                CakeTableDAO dao = new CakeTableDAO();
                CakeTableDTO product = dao.getProduct(cakeID);

                if (product != null) {
                    CartObject cart = (CartObject) ses.getAttribute("SES_CART");
                    if (cart == null) {
                        cart = new CartObject();
                    }

                    cart.addItem(product);
                    ses.setAttribute("SES_CART", cart);
                    msg = "added item to cart";
                    hasError = false;
                }
            }
        } catch (Throwable t) {
            hasError = true;
            msg = "product quantity reached";
            log("AddItemServlet-" + t.getMessage());
        } finally {
            if (hasError) {
                toReturn.addProperty("result", ERROR_RESULT);
            } else {
                toReturn.addProperty("result", SUCCESS_RESULT);
            }
            toReturn.addProperty("msg", msg);
            out.write(new Gson().toJson(toReturn));

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
