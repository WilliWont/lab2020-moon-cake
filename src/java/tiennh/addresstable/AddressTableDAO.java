/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.addresstable;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiennh.util.DBHelper;

public class AddressTableDAO implements Serializable {

    public int saveAddress(AddressTableDTO address)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        int result = -1;

        try {
            String query = "INSERT INTO addressTbl VALUES(?,?,?,?,?,?,?,?)";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int paramCount = 1;
            preStm.setString(paramCount++, address.getUsername());
            preStm.setString(paramCount++, address.getPhone());
            preStm.setString(paramCount++, address.getNameF() + " " + address.getNameL());
            preStm.setString(paramCount++, address.getAddrLine());
            preStm.setString(paramCount++, address.getWard());
            preStm.setString(paramCount++, address.getDistrict());
            preStm.setString(paramCount++, address.getCity());
            preStm.setInt(paramCount++, DBHelper.DEFAULT_STATUS);

            preStm.executeUpdate();

            rs = preStm.getGeneratedKeys();

            if (rs.next()) {
                result = rs.getInt(1);
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

    public List<AddressTableDTO> getUserAddress(String username)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<AddressTableDTO> result = null;

        try {
            String query = "SELECT id, phone, name, addressLine, addressWard, "
                    + "addressDistrict, addressCity "
                    + "FROM addressTbl "
                    + "WHERE status = ? AND username = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, DBHelper.ACTIVE_STATUS);
            preStm.setString(2, username);

            rs = preStm.executeQuery();

            while (rs.next()) {
                if (result == null) {
                    result = new ArrayList();
                }

                AddressTableDTO address = new AddressTableDTO();
                address.setId(rs.getInt("id"));
                address.setPhone(rs.getString("phone"));
                address.setNameFull(rs.getString("name"));
                address.setAddrLine(rs.getString("addressLine"));
                address.setWard(rs.getString("addressWard"));
                address.setDistrict(rs.getString("addressDistrict"));
                address.setCity(rs.getString("addressCity"));

                result.add(address);
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

    public AddressTableDTO getAddress(int addrID)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        AddressTableDTO result = null;

        try {
            String query = "SELECT id, phone, name, addressLine, addressWard, "
                    + "addressDistrict, addressCity "
                    + "FROM addressTbl "
                    + "WHERE status = ? AND id = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, DBHelper.ACTIVE_STATUS);
            preStm.setInt(2, addrID);

            rs = preStm.executeQuery();

            if (rs.next()) {

                AddressTableDTO address = new AddressTableDTO();
                address.setId(rs.getInt("id"));
                address.setPhone(rs.getString("phone"));
                address.setNameFull(rs.getString("name"));
                address.setAddrLine(rs.getString("addressLine"));
                address.setWard(rs.getString("addressWard"));
                address.setDistrict(rs.getString("addressDistrict"));
                address.setCity(rs.getString("addressCity"));

                result = address;
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

    public AddressTableDTO getAddressByOrderID(int orderID)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        AddressTableDTO result = null;

        try {
            String query = "SELECT b.phone, b.name, b.addressLine, "
                    + "b.addressWard, b.addressDistrict, b.addressCity "
                    + "FROM"
                    + "(SELECT address "
                    + "FROM orderTbl "
                    + "WHERE id = ?) a "
                    + "JOIN "
                    + "(SELECT id, phone, name, addressLine, addressWard, "
                    + "addressDistrict, addressCity "
                    + "FROM addressTbl) b "
                    + "ON a.address = b.id";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, orderID);

            rs = preStm.executeQuery();

            if (rs.next()) {

                AddressTableDTO address = new AddressTableDTO();
                address.setPhone(rs.getString("phone"));
                address.setNameFull(rs.getString("name"));
                address.setAddrLine(rs.getString("addressLine"));
                address.setWard(rs.getString("addressWard"));
                address.setDistrict(rs.getString("addressDistrict"));
                address.setCity(rs.getString("addressCity"));

                result = address;
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
