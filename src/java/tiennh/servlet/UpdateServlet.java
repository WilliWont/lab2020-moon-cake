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
import javax.servlet.http.Part;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.util.CakeHelper;

/**
 *
 * @author Will
 */
public class UpdateServlet extends HttpServlet {

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

        String txtCakeID = request.getParameter("txtCakeID");
        Integer cakeID;
        try {
            cakeID = Integer.parseInt(txtCakeID);
        } catch (Throwable t) {
            cakeID = null;
        }

        boolean hasError = false;

        String returnMsg = "error, ";

        try {
            if (cakeID != null) {
                Part imagePart = request.getPart("txtCakeImg");
                Boolean toUpdateImg = request.getParameter("toUpdateImg") != null;
                if (!toUpdateImg) {
                    imagePart = null;
                }
                CakeTableDTO cake = CakeHelper.getCakeParameters(request);
                cake.setId(cakeID);
                cake.setImage(toUpdateImg);
                String name = cake.getName();
                Float price = cake.getPrice();

                // <editor-fold defaultstate="collapsed" desc="Name Validation">//GEN-BEGIN:initComponents            
                if (name == null || (name = name.trim()).length() <= CakeHelper.NAME_MIN_LEN) {
                    returnMsg += "empty name field\n";
                    hasError = true;
                } else if (name.length() > CakeHelper.NAME_MAX_LEN) {
                    returnMsg += "name cannot exceed " + CakeHelper.NAME_MAX_LEN + " characters\n";
                    hasError = true;
                }
                // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc="Date Validation">//GEN-BEGIN:initComponents            
                if (cake.getCreateDate() == null) {
                    returnMsg += "empty creation date\n";
                    hasError = true;
                } else if (cake.getExpireDate() == null) {
                    returnMsg += "empty expiration date\n";
                    hasError = true;
                } else if (cake.getCreateDate().after(cake.getExpireDate())) {
                    returnMsg += "creation date cannot be after expiration date\n";
                    hasError = true;
                }

                // </editor-fold>
                // <editor-fold defaultstate="collapsed" desc="Price Validation">//GEN-BEGIN:initComponents
                if (price < CakeHelper.MIN_PRICE_RANGE) {
                    returnMsg += "price cannot be less than 0\n";
                    hasError = true;
                }
                // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc="Quantity Validation">//GEN-BEGIN:initComponents
                if (price < CakeHelper.MIN_PRICE_RANGE) {
                    returnMsg += "quantity cannot be less than 0\n";
                    hasError = true;
                }
                // </editor-fold>

                // <editor-fold defaultstate="collapsed" desc="Img Validation">//GEN-BEGIN:initComponents
                if (imagePart != null
                        && imagePart.getSize() > CakeHelper.IMAGE_MAX_SIZE_VALUE) {
                    returnMsg += "image cannot exceed "
                            + CakeHelper.IMAGE_MAX_SIZE + " MBs\n";
                    hasError = true;
                }
                // </editor-fold>

                if (!hasError) {
                    CakeTableDAO dao = new CakeTableDAO();
                    int result = dao.updateCake(cake, imagePart);

                    if (result < 0) {
                        hasError = true;
                        returnMsg += "unable to update cake\n";
                    }
                } // exit if invalid form
            } //exit if cake id == null
            else {
                returnMsg += "unable to locate cake\n";
                hasError = true;
            }
        } catch (Throwable t) {
            hasError = true;
            returnMsg += "unable to update cake\n";
            log(this.getClass().getSimpleName() + '-' + t.getMessage());
        } finally {
            if (!hasError) {
                returnMsg = "cake updated successfully";
            }

            out.write(returnMsg);
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
