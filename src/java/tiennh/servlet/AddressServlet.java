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
import tiennh.addresstable.AddressTableDAO;
import tiennh.addresstable.AddressTableDTO;
import tiennh.usertable.UserTableDTO;

/**
 *
 * @author Will
 */
public class AddressServlet extends HttpServlet {

    private final String CHECKOUT_PAGE = "checkout";
    private final String ADDRESS_PAGE = "addressPage";

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

        boolean hasAddress = false;

        try {
            HttpSession ses = request.getSession();
            UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
            if (user != null) {
                AddressTableDAO dao = new AddressTableDAO();
                List<AddressTableDTO> addresses = dao.getUserAddress(user.getUsername());

                if (addresses != null && addresses.size() > 0) {
                    hasAddress = true;
                    request.setAttribute("ADDR_LIST", addresses);
                }
            }
        } catch (Throwable t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            if (!hasAddress) {
                response.sendRedirect(CHECKOUT_PAGE);
            } else {
                ServletContext ctx = request.getServletContext();
                String addr
                        = ((ResourceBundle) ctx.getAttribute("SITE_MAP"))
                                .getString(ADDRESS_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(addr);
                rd.forward(request, response);
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
