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
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.cart.CartObject;

/**
 *
 * @author Will
 */
public class QuantityServlet extends HttpServlet {

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
        String txtQuantity = request.getParameter("txtQuantity");
        String txtCakeID = request.getParameter("txtCakeID");
        Integer quantity;
        Integer cakeID;
        HttpSession ses = request.getSession();
        CartObject cart = (CartObject) ses.getAttribute("SES_CART");

        try {
            cakeID = Integer.parseInt(txtCakeID);
            quantity = Integer.parseInt(txtQuantity);
            if (quantity == 0) {
                throw new Exception();
            }
        } catch (Throwable t) {
            quantity = null;
            cakeID = null;
        }
        JsonObject toReturn = new JsonObject();
        Boolean hasError = true;
        String msg = "error updating product quantity";
        try {
            if (quantity != null && cakeID != null && cart != null) {
                CakeTableDAO dao = new CakeTableDAO();
                int result = dao.checkCakeQuantity(cakeID, quantity);
                if (result > 0) {
                    CakeTableDTO cake = new CakeTableDTO();
                    cake.setId(result);
                    cake.setQuantity(quantity);
                    cart.updateItem(cake);
                    ses.setAttribute("SES_CART", cart);
                    hasError = false;
                    toReturn.addProperty("total", cart.getCartTotal());
                    msg = "product quantity updated";
                } else {
                    msg = "product quantity capped";
                }
                CakeTableDTO cake = cart.getItem(cakeID);
                toReturn.addProperty("quantity", cake.getQuantity());
            }
        } catch (Throwable t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
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
