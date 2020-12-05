/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.google.GoogleObject;
import tiennh.usertable.UserTableDAO;
import tiennh.usertable.UserTableDTO;
import tiennh.util.AccountHelper;
import tiennh.util.GoogleHelper;

/**
 *
 * @author Will
 */
public class GoogleOAuthServlet extends HttpServlet {

    private final String STORE_SERVLET = "store";


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
        System.out.println("--OAUTH--");
        PrintWriter out = response.getWriter();

        try {
            String code = request.getParameter("code");
            if (code != null && !code.isEmpty()) {
                String accessToken = GoogleHelper.getToken(code);
                GoogleObject user = GoogleHelper.getUserInfo(accessToken);
                
                UserTableDTO userDTO = new UserTableDTO();
                userDTO.setNameF(user.getEmail());
                userDTO.setNameL(user.getFamily_name());
                userDTO.setUsername(user.getId());
                userDTO.setRole(AccountHelper.THIRD_PARTY_ROLE);
                HttpSession ses = request.getSession();
                
                ses.setAttribute("SES_USER", userDTO);
                
                UserTableDAO dao = new UserTableDAO();
                dao.insert3rdParty(userDTO.getUsername());
            }

        } catch (Exception t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            response.sendRedirect(STORE_SERVLET);
            
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
