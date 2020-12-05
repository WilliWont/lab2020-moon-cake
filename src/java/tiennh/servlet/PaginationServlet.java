/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.usertable.UserTableDTO;
import tiennh.util.AccountHelper;
import tiennh.util.CakeHelper;
import tiennh.util.DBHelper;

/**
 *
 * @author Will
 */
public class PaginationServlet extends HttpServlet {

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

        String searchValue = request.getParameter("txtSearch");
        JsonArray cakeJson = null;

        try {
            int curPage = Integer.parseInt(request.getParameter("curPage"));

            if (searchValue != null) {
                searchValue = searchValue.trim();
            }

            Integer cateID = CakeHelper.getCateIDParam(request);
            Float priceMax = CakeHelper.getFloatParam(request, "txtMaxPrice");
            Float priceMin = CakeHelper.getFloatParam(request, "txtMinPrice");

            if (priceMax != null && priceMin != null) {
                HttpSession ses = request.getSession();
                UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
                boolean isAdmin = (user != null
                        && user.getRole() == AccountHelper.ADMIN_ROLE);

                int start = (curPage - 1) * DBHelper.PER_PAGE;
                int end = start + DBHelper.PER_PAGE + 1;
                CakeTableDAO dao = new CakeTableDAO();
                List<CakeTableDTO> cakeList
                        = dao.searchPaginated(start, end, searchValue,
                                priceMin, priceMax,
                                cateID, isAdmin);
                cakeJson = CakeHelper.listToJson(cakeList);
            }
        } catch (Throwable t) {
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            out.write(new Gson().toJson(cakeJson));

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
