/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.caketable;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import tiennh.util.CakeHelper;
import tiennh.util.DBHelper;

public class CakeTableDAO implements Serializable {

    public List<CakeTableDTO> searchProduct(String name,
            Float priceMin, Float priceMax,
            Integer cateID, boolean isAdmin)
            throws SQLException, NamingException {
//        "min:"+priceMin);
//        "max:"+priceMax);

        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<CakeTableDTO> toReturn = null;

        try {
            String query = "SELECT a.id, a.name, a.image, a.price, a.quantity, "
                    + "a.categoryID, b.name AS categoryName "
                    + "FROM "
                    + "(SELECT id, name, description, image, quantity, price, categoryID "
                    + "FROM cakeTbl "
                    + "WHERE name LIKE ? AND (price >= ? AND price <= ?) ";
            if (cateID != null) {
                query += "AND categoryID = ? ";
            }
            query += "AND status = ?  ";
            if (!isAdmin) {
                query += "AND quantity > ? ";
            }
            query += ") a "
                    + "JOIN "
                    + "(SELECT id, name "
                    + "FROM categoryTbl ) b "
                    + "ON a.categoryID = b.id "
                    + "ORDER BY b.id ";
            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            int paramCount = 1;
            preStm.setString(paramCount++, '%' + name + '%');
            preStm.setFloat(paramCount++, priceMin);
            preStm.setFloat(paramCount++, priceMax);

            if (cateID != null) {
                preStm.setInt(paramCount++, cateID);
            }

            preStm.setInt(paramCount++, DBHelper.ACTIVE_STATUS);

            if (!isAdmin) {
                preStm.setInt(paramCount++, CakeHelper.MIN_QUANTITY);
            }

            rs = preStm.executeQuery();
            int count = 0;
            while (rs.next()) {

                if (toReturn == null) {
                    toReturn = new ArrayList();
                }

                CakeTableDTO received = new CakeTableDTO();
                received.setId(rs.getInt("id"));
                received.setName(rs.getString("name"));
                received.setImage(rs.getBoolean("image"));
                received.setPrice(rs.getFloat("price"));
                received.setQuantityStock(rs.getInt("quantity"));
                received.setCategoryID(rs.getInt("categoryID"));
                received.setCategoryName(rs.getString("categoryName"));
                if (rs.getBoolean("image")) {
                    received.setImagePath(CakeHelper.IMAGE_TAG_PATH + received.getId() + CakeHelper.IMAGE_EXTENSION);
                } else {
                    received.setImagePath(CakeHelper.IMAGE_TAG_PATH + '0' + CakeHelper.IMAGE_EXTENSION);
                }
                count++;

                toReturn.add(received);
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

        return toReturn;
    }

    public List<CakeTableDTO> searchPaginated(int start, int end, String name,
            Float priceMin, Float priceMax,
            Integer cateID, boolean isAdmin)
            throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<CakeTableDTO> toReturn = null;

        try {
            String query = "SELECT a.id, a.name, a.image, a.price, a.quantity, "
                    + "a.categoryID, b.name AS categoryName "
                    + "FROM "
                    + "(SELECT id, name, description, image, quantity, price, categoryID "
                    + "FROM cakeTbl "
                    + "WHERE name LIKE ? AND (price >= ? AND price <= ?) ";
            if (cateID != null) {
                query += "AND categoryID = ? ";
            }
            query += "AND status = ? ";
            if (!isAdmin) {
                query += "AND quantity > ? ";
            }
            query += "ORDER BY id "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY "
                    + ") a "
                    + "JOIN "
                    + "(SELECT id, name "
                    + "FROM categoryTbl ) b "
                    + "ON a.categoryID = b.id "
                    + "ORDER BY b.id ";
            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            int paramCount = 1;
            preStm.setString(paramCount++, '%' + name + '%');
            preStm.setFloat(paramCount++, priceMin);
            preStm.setFloat(paramCount++, priceMax);

            if (cateID != null) {
                preStm.setInt(paramCount++, cateID);
            }

            preStm.setInt(paramCount++, DBHelper.ACTIVE_STATUS);

            if (!isAdmin) {
                preStm.setInt(paramCount++, CakeHelper.MIN_QUANTITY);
            }

            preStm.setInt(paramCount++, start);
            preStm.setInt(paramCount++, end);

            rs = preStm.executeQuery();
            int toStop = DBHelper.PER_PAGE;
            int count = 0;
            while (rs.next() && count <= toStop) {

                if (toReturn == null) {
                    toReturn = new ArrayList();
                }

                CakeTableDTO received = new CakeTableDTO();
                received.setId(rs.getInt("id"));
                received.setName(rs.getString("name"));
                received.setImage(rs.getBoolean("image"));
                received.setPrice(rs.getFloat("price"));
                received.setQuantityStock(rs.getInt("quantity"));
                received.setCategoryID(rs.getInt("categoryID"));
                received.setCategoryName(rs.getString("categoryName"));
                if (rs.getBoolean("image")) {
                    received.setImagePath(CakeHelper.IMAGE_TAG_PATH + received.getId() + CakeHelper.IMAGE_EXTENSION);
                } else {
                    received.setImagePath(CakeHelper.IMAGE_TAG_PATH + '0' + CakeHelper.IMAGE_EXTENSION);
                }
                count++;

                toReturn.add(received);
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

        return toReturn;
    }

    public Map<Integer, Integer> getProductQuantity()
            throws SQLException, NamingException {

        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        Map<Integer, Integer> toReturn = null;

        try {
            String query = "SELECT id, quantity FROM cakeTbl ";
            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            rs = preStm.executeQuery();
            while (rs.next()) {
                if (toReturn == null) {
                    toReturn = new HashMap();
                }

                toReturn.put(rs.getInt("id"), rs.getInt("quantity"));
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

        return toReturn;

    }

    public CakeTableDTO getProduct(int productID)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        CakeTableDTO toReturn = null;
        try {
            String query = "SELECT a.id, a.name, a.image, a.price, a.quantity, "
                    + "b.name AS categoryName "
                    + "FROM "
                    + "(SELECT id, name, image, price, categoryID, quantity "
                    + "FROM cakeTbl "
                    + "WHERE id = ? "
                    + "AND status = ? "
                    + "AND quantity > ? "
                    + ") a "
                    + "JOIN "
                    + "(SELECT id, name "
                    + "FROM categoryTbl ) b "
                    + "ON a.categoryID = b.id ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            int stmCount = 1;
            preStm.setInt(stmCount++, productID);
            preStm.setInt(stmCount++, DBHelper.DEFAULT_STATUS);
            preStm.setInt(stmCount++, CakeHelper.MIN_QUANTITY);

            rs = preStm.executeQuery();

            if (rs.next()) {
                CakeTableDTO cake = new CakeTableDTO();
                cake.setId(rs.getInt("id"));
                cake.setName(rs.getString("name"));
                cake.setPrice(rs.getFloat("price"));
                cake.setCategoryName(rs.getString("categoryName"));
                cake.setQuantityStock(rs.getInt("quantity"));
                if (rs.getBoolean("image")) {
                    cake.setImagePath(CakeHelper.IMAGE_TAG_PATH + cake.getId() + CakeHelper.IMAGE_EXTENSION);
                } else {
                    cake.setImagePath(CakeHelper.IMAGE_TAG_PATH + '0' + CakeHelper.IMAGE_EXTENSION);
                }
                toReturn = cake;
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

        return toReturn;
    }

    public CakeTableDTO getProductForUpdate(int productID)
            throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        CakeTableDTO toReturn = null;
        try {
            String query = "SELECT name, description, price, createDate, "
                    + "expireDate, quantity, categoryID, image "
                    + "FROM cakeTbl "
                    + "WHERE status = ? AND id = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, DBHelper.ACTIVE_STATUS);
            preStm.setInt(2, productID);

            rs = preStm.executeQuery();

            if (rs.next()) {
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                CakeTableDTO cake = new CakeTableDTO();
                cake.setName(rs.getString("name"));
                cake.setDescription(rs.getString("description"));
                cake.setPrice(rs.getFloat("price"));
                cake.setCreateDate(rs.getDate("createDate"));
                cake.setExpireDate(rs.getDate("expireDate"));
                cake.setQuantityStock(rs.getInt("quantity"));
                cake.setCategoryID(rs.getInt("categoryID"));
                if (rs.getBoolean("image")) {
                    cake.setImagePath(CakeHelper.IMAGE_TAG_PATH + productID + CakeHelper.IMAGE_EXTENSION);
                } else {
                    cake.setImagePath(CakeHelper.IMAGE_TAG_PATH + '0' + CakeHelper.IMAGE_EXTENSION);
                }
                toReturn = cake;
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

        return toReturn;
    }

    public int checkCakeQuantity(int id, int quantity)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        int result = -1;
        try {
            String query = "SELECT id "
                    + "FROM cakeTbl "
                    + "WHERE status = ? AND quantity >= ? AND id = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);
            preStm.setInt(1, DBHelper.ACTIVE_STATUS);
            preStm.setInt(2, quantity);
            preStm.setInt(3, id);

            rs = preStm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
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

    public int createCake(CakeTableDTO cake,
            Part image)
            throws NamingException, SQLException, IOException, InterruptedException {

        int result = -1;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            String query = "INSERT INTO cakeTbl "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);
            preStm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int stmCount = 1;
            preStm.setString(stmCount++, cake.getName());
            preStm.setString(stmCount++, cake.getDescription());
            preStm.setFloat(stmCount++, cake.getPrice());
            preStm.setDate(stmCount++, new Date(cake.getCreateDate().getTime()));
            preStm.setDate(stmCount++, new Date(cake.getExpireDate().getTime()));
            preStm.setInt(stmCount++, cake.getQuantity());
            preStm.setInt(stmCount++, DBHelper.DEFAULT_STATUS);
            preStm.setInt(stmCount++, cake.getCategoryID());
            preStm.setBoolean(stmCount++, cake.getImage());

            int rowsAffected = preStm.executeUpdate();

            rs = preStm.getGeneratedKeys();

            if (rs.next()) {
                result = rs.getInt(1);
            }

            if (cake.getImage()) {
                boolean uploadResult
                        = CakeHelper.uploadImage(Integer.toString(result), image);
                if (uploadResult) {
                    conn.commit();
                } else {
                    conn.rollback();
                    result = -1;
                }
            } else {
                conn.commit();
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

    public int updateCake(CakeTableDTO cake,
            Part image)
            throws NamingException, SQLException, IOException, InterruptedException {
        // TODO: REFACTOR THIS
        int result = -1;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            String query = "UPDATE cakeTbl SET name = ?, description = ?, "
                    + "price = ?, createDate = ?, "
                    + "expireDate = ?, quantity = ?, "
                    + "categoryID = ? ";
            if (image != null) {
                query += ", image = ? ";
            }

            query += "WHERE status = ? AND id = ? ";

            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);
            preStm = conn.prepareStatement(query);
            // TODO: SET ID OF DTO
            int stmCount = 1;
            preStm.setString(stmCount++, cake.getName());
            preStm.setString(stmCount++, cake.getDescription());
            preStm.setFloat(stmCount++, cake.getPrice());
            preStm.setDate(stmCount++, new Date(cake.getCreateDate().getTime()));
            preStm.setDate(stmCount++, new Date(cake.getExpireDate().getTime()));
            preStm.setInt(stmCount++, cake.getQuantity());
            preStm.setInt(stmCount++, cake.getCategoryID());

            if (image != null) {
                preStm.setBoolean(stmCount++, cake.getImage());
            }

            preStm.setInt(stmCount++, DBHelper.DEFAULT_STATUS);
            preStm.setInt(stmCount++, cake.getId());

            int rowsAffected = preStm.executeUpdate();


            if (rowsAffected > 0) {
                if (cake.getImage() && image != null) {
                    boolean uploadResult
                            = CakeHelper.uploadImage(cake.getId().toString(), image);
                    if (uploadResult) {
                        conn.commit();
                    } else {
                        conn.rollback();
                    }
                } // do if image
                else {
                    conn.commit();
                } // do if no image
                result = rowsAffected;
            } //exit if no updates was made
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

    public void updateCakeQuantity(List<CakeTableDTO> cakeList)
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        try {
            String query = "UPDATE cakeTbl "
                    + "SET quantity = quantity - ? "
                    + "WHERE id = ? ";

            conn = DBHelper.getConnection();
            conn.setAutoCommit(false);

            preStm = conn.prepareStatement(query);

            for (CakeTableDTO cake : cakeList) {
                int paramCount = 1;
                preStm.setInt(paramCount++, cake.getQuantity());
                preStm.setInt(paramCount++, cake.getId());

                preStm.addBatch();
            }

            int[] queryResult = preStm.executeBatch();

            if (queryResult != null) {
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

    public List<Integer> getPriceRangeInStock()
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<Integer> result = null;

        try {
            String query = "SELECT MAX(price) AS max, MIN(price) as min "
                    + "FROM cakeTbl "
                    + "WHERE status = ? AND quantity > ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, DBHelper.DEFAULT_STATUS);
            preStm.setInt(2, CakeHelper.MIN_QUANTITY);

            rs = preStm.executeQuery();

            if (rs.next()) {
                result = new ArrayList();
                result.add((int) Math.ceil(rs.getDouble("max")));
                result.add((int) Math.floor(rs.getDouble("min")));
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

    public List<Integer> getPriceRange()
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<Integer> result = null;

        try {
            String query = "SELECT MAX(price) AS max, MIN(price) as min "
                    + "FROM cakeTbl "
                    + "WHERE status = ? ";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            preStm.setInt(1, DBHelper.DEFAULT_STATUS);

            rs = preStm.executeQuery();

            if (rs.next()) {
                result = new ArrayList();
                result.add((int) Math.ceil(rs.getDouble("max")));
                result.add((int) Math.floor(rs.getDouble("min")));
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
