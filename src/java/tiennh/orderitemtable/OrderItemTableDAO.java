/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.orderitemtable;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiennh.caketable.CakeTableDTO;
import tiennh.util.DBHelper;

public class OrderItemTableDAO implements Serializable {

    public void insertOrderItem(List<CakeTableDTO> itemList, int orderID)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            String query = "INSERT INTO orderItemTbl "
                    + "VALUES (?,?,?,?,?) ";

            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            preStm = conn.prepareStatement(query);

            for (CakeTableDTO cake : itemList) {
                int paramCount = 1;
                preStm.setInt(paramCount++, orderID);
                preStm.setInt(paramCount++, cake.getId());
                preStm.setString(paramCount++, cake.getName());
                preStm.setInt(paramCount++, cake.getQuantity());
                preStm.setFloat(paramCount++, cake.getQuantity()
                        * cake.getPrice());

                preStm.addBatch();
            }
            int[] queryResult = preStm.executeBatch();
            if (queryResult != null && queryResult.length > 0) {
                conn.commit();
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

    }

    public boolean checkOrderOwner(String username)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        boolean result = false;

        try {
            String query = "SELECT id "
                    + "FROM orderTbl "
                    + "WHERE userID = ?";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setString(1, username);

            rs = preStm.executeQuery();

            result = rs.next();
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

    public List<CakeTableDTO> getOrderItem(int orderID)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<CakeTableDTO> result = null;

        try {
            String query = "SELECT itemName, itemQuantity, itemTotal "
                    + "FROM orderItemTbl "
                    + "WHERE orderID = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, orderID);

            rs = preStm.executeQuery();

            DecimalFormat df = new DecimalFormat("###.##");
            while (rs.next()) {
                CakeTableDTO cake = new CakeTableDTO();
                cake.setName(rs.getString("itemName"));
                cake.setQuantity(rs.getInt("itemQuantity"));
                Float price = Float.parseFloat(df.format(rs.getFloat("itemTotal")));
                cake.setPrice(price);

                if (result == null) {
                    result = new ArrayList();
                }

                result.add(cake);
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
