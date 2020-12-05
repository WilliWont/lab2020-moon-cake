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
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.addresstable.AddressTableDTO;
import tiennh.caketable.CakeTableDTO;
import tiennh.cart.CartObject;
import tiennh.ordertable.OrderTableDAO;
import tiennh.usertable.UserTableDTO;
import tiennh.util.AddressHelper;
import tiennh.util.CheckoutHelper;

/**
 *
 * @author Will
 */
public class ConfirmCheckoutServlet extends HttpServlet {

    private final String CHK_VALID = "checkoutValid";

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
        HttpSession ses = request.getSession();
        UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
        JsonObject error = new JsonObject();
        int orderID = -1;
        try {
            AddressTableDTO address = AddressHelper.getAddressParameter(request);
            CartObject cart = (CartObject) ses.getAttribute("SES_CART");
            if (cart != null) {
                List<CakeTableDTO> productList = cart.getProductList();
                if (productList != null) {

                    System.out.println("validate addr");
                    if (address.getId() == null) {
                        error = AddressHelper.validateAddress(address);
                    } else {
                        error = null;
                    }

                    System.out.println("validate quantity");
                    // <editor-fold defaultstate="collapsed" desc="Quantity Code">//GEN-BEGIN:initComponents
                    Map<Integer, Integer> quantityCheck
                            = CheckoutHelper.checkQuantity(productList);

                    if (quantityCheck != null) {
                        StringBuilder error_msg = new StringBuilder();
                        for (Integer id : quantityCheck.keySet()) {
                            System.out.println("update quantity of " + id);
                            CakeTableDTO cake = new CakeTableDTO();
                            cake.setId(id);
                            cake.setQuantity(quantityCheck.get(id));
                            cart.updateItem(cake);
                            error_msg.append(id)
                                    .append(',')
                                    .append(cake.getQuantity())
                                    .append(';');
                        }
                        ses.setAttribute("SES_CART", cart);
                        System.out.println("error_msg:" + error_msg);
                        if (error == null) {
                            error = new JsonObject();
                        }
                        error.addProperty("msg", "product stock has changed");
                        error.addProperty("item_error", error_msg.toString());
                        error.addProperty("cart_price", cart.getCartTotal());
                    }
                    // </editor-fold>

                    if (error == null) {
                        System.out.println("insert order");
                        // <editor-fold defaultstate="collapsed" desc="Checkout Code">//GEN-BEGIN:initComponents
                        try {
                            String userID = AddressHelper.DEFAULT_USER;
                            if (user != null && user.getUsername() != null) {
                                userID = user.getUsername();
                            }

                            OrderTableDAO orderDAO = new OrderTableDAO();
                            orderID = orderDAO.confirmOrder(address, productList, userID);
                        } catch (Throwable t) {
                            log("ConfirmCheckoutServlet-billing-" + t.getMessage());
                            if (error == null) {
                                error = new JsonObject();
                            }

                            error.addProperty("general_error", "checkout unavailable, "
                                    + "please try again");
                        }

                        if (orderID > -1) {
                            ses.setAttribute("SES_CART", null);
                            error = null;
                        }
                        // </editor-fold>
                    }

                }
            }
        } catch (Throwable t) {
            log("ConfirmCheckoutServlet-" + t.getMessage());
        } finally {
            if (error != null) {
                System.out.println("error not null");
                error.addProperty("result", "error");
                out.write(new Gson().toJson(error));
            } else {
                System.out.println("error null");
                JsonObject result = new JsonObject();
                result.addProperty("result", "success");
                result.addProperty("addr",
                        request.getServletContext().getContextPath() + "/"
                        + CHK_VALID + "?txtOrderID=" + orderID);
                out.write(new Gson().toJson(result));
            }

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
