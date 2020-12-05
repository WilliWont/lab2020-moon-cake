/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.addresstable.AddressTableDAO;
import tiennh.addresstable.AddressTableDTO;
import tiennh.caketable.CakeTableDTO;
import tiennh.orderitemtable.OrderItemTableDAO;
import tiennh.usertable.UserTableDTO;
import tiennh.util.CakeHelper;
import tiennh.util.DBHelper;

/**
 *
 * @author Will
 */
public class GetOrderServlet extends HttpServlet {

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
        System.out.println("--" + this.getClass().getSimpleName() + "--");
        PrintWriter out = response.getWriter();
        HttpSession ses = request.getSession();
        UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
        int orderID;
        String txtOrderID = DBHelper.filterHTML(request.getParameter("txtOrderID"));

        boolean hasError = true;
        JsonObject msg = null;
        JsonArray toReturn = null;
        JsonObject cart = null;
        JsonObject address = null;
        try {
            String username = user.getUsername();
            OrderItemTableDAO itemDAO = new OrderItemTableDAO();
            if (itemDAO.checkOrderOwner(username)) {
                orderID = Integer.parseInt(txtOrderID);

                List<CakeTableDTO> itemList = itemDAO.getOrderItem(orderID);
                toReturn = CakeHelper.listToJson(itemList);

                Float cartTotal = 0f;
                Integer cartAmount = itemList.size();
                for (CakeTableDTO cake : itemList) {
                    cartTotal += cake.getPrice();
                }
                DecimalFormat df = new DecimalFormat("###.##");
                cartTotal = Float.parseFloat(df.format(cartTotal));

                cart = new JsonObject();
                cart.addProperty("cart_amount", cartAmount);
                cart.addProperty("cart_total", cartTotal);

                AddressTableDAO addrDAO = new AddressTableDAO();
                AddressTableDTO addr = addrDAO.getAddressByOrderID(orderID);
                address = new JsonObject();
                address.addProperty("address", addr.addressToString());
                address.addProperty("name", addr.getNameFull());
                address.addProperty("phone", addr.getPhone());

                hasError = false;
            }
        } catch (Throwable t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            msg = new JsonObject();
            if (hasError) {
                msg.addProperty("result", "error");
                msg.addProperty("msg", "could not retrieve order info, please try again");
            } else {
                msg.addProperty("result", "success");
            }
            out.write(new Gson().toJson(msg));
            out.write("&");
            if (!hasError) {
                out.write(new Gson().toJson(toReturn));
                out.write("&");
                out.write(new Gson().toJson(cart));
                out.write("&");
                out.write(new Gson().toJson(address));
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
