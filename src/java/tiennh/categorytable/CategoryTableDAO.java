/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.categorytable;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tiennh.util.DBHelper;

public class CategoryTableDAO implements Serializable {

    public List<CategoryTableDTO> getAllCategory()
            throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        List<CategoryTableDTO> result = null;

        try {
            String query = "SELECT id, name FROM categoryTbl";

            conn = DBHelper.getConnection();

            preStm = conn.prepareStatement(query);

            rs = preStm.executeQuery();

            while (rs.next()) {
                if (result == null) {
                    result = new ArrayList<>();
                }

                CategoryTableDTO cate = new CategoryTableDTO();
                cate.setId(rs.getInt("id"));
                cate.setName(rs.getString("name"));

                result.add(cate);
            }
            System.out.println("got cate amount: " + result.size());
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
