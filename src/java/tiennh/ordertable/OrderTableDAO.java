/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.ordertable;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiennh.addresstable.AddressTableDAO;
import tiennh.addresstable.AddressTableDTO;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;
import tiennh.orderitemtable.OrderItemTableDAO;
import tiennh.util.CheckoutHelper;
import tiennh.util.DBHelper;
import tiennh.util.OrderHelper;

public class OrderTableDAO implements Serializable {

    public int confirmOrder(AddressTableDTO address,
            List<CakeTableDTO> cakeList,
            String userID)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        int result = -1;

        try {
            int addrID;
            if (address.getId() != null) {
                addrID = address.getId();
                System.out.println("address to insert: " + addrID);
            } else {
                System.out.println("new address");
                AddressTableDAO addrDAO = new AddressTableDAO();
                addrID = addrDAO.saveAddress(address);
            }
            String query = "INSERT INTO orderTbl "
                    + "VALUES(?,?,?,?,?,?) ";

            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            preStm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int paramCount = 1;
            preStm.setString(paramCount++, userID);
            preStm.setTimestamp(paramCount++, new Timestamp(new java.util.Date().getTime()));
            preStm.setInt(paramCount++, CheckoutHelper.DEFAULT_PAYMENT_METHOD);
            preStm.setInt(paramCount++, CheckoutHelper.DEFAULT_PAYMENT_STATUS);
            preStm.setInt(paramCount++, addrID);
            preStm.setTimestamp(paramCount++, null);

            preStm.executeUpdate();
            rs = preStm.getGeneratedKeys();
            if (rs.next()) {
                int orderID = rs.getInt(1);
                conn.commit();

                CakeTableDAO cakeDAO = new CakeTableDAO();
                cakeDAO.updateCakeQuantity(cakeList);

                OrderItemTableDAO itemDAO = new OrderItemTableDAO();
                itemDAO.insertOrderItem(cakeList, orderID);

                result = orderID;
            } else {
                conn.rollback();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public List<OrderTableDTO> getOrder(String username)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<OrderTableDTO> result = null;

        try {
            String query = "SELECT id, orderDate, paymentMethod, "
                    + "paymentStatus, "
                    + "address, paymentDate "
                    + "FROM orderTbl "
                    + "WHERE userID = ? "
                    + "ORDER BY orderDate DESC ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setString(1, username);

            rs = preStm.executeQuery();

            OrderHelper helper = new OrderHelper();

            while (rs.next()) {
                if (result == null) {
                    result = new ArrayList();
                }

                OrderTableDTO order = new OrderTableDTO();

                order.setId(rs.getInt("id"));
                order.setOrderDate(rs.getTimestamp("orderDate"));
                order.setPaymentMethod(helper.getMethod(rs.getInt("paymentMethod")));
                order.setPaymentStatus(helper.getStatus(rs.getInt("paymentStatus")));
                order.setAddress(rs.getInt("address"));
                order.setPaymentDate(rs.getTimestamp("paymentDate"));

                result.add(order);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
}
