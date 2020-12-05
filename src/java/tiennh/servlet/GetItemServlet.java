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
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.util.CakeHelper;
import tiennh.util.DBHelper;

/**
 *
 * @author Will
 */
public class GetItemServlet extends HttpServlet {

    private final String ERROR_MSG = "error, unable to get cake information";

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
        System.out.println("--" + this.getClass().getSimpleName() + "--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String returnMsg = ERROR_MSG;
        JsonObject toReturn = new JsonObject();
        String txtCakeID = DBHelper.filterHTML(request.getParameter("txtCakeID"));

        try {
            int cakeID = Integer.parseInt(txtCakeID);
            CakeTableDAO dao = new CakeTableDAO();
            CakeTableDTO cake = dao.getProductForUpdate(cakeID);
            if (cake == null) {
                throw new Exception("no cake found");
            }

            cake.setId(cakeID);
            toReturn = CakeHelper.cakeToJson(cake);

        } catch (Throwable t) {
            toReturn.addProperty("error", returnMsg);
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {

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
