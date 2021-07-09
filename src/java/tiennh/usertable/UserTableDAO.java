/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.usertable;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tiennh.util.AccountHelper;
import tiennh.util.DBHelper;

public class UserTableDAO implements Serializable {

      public boolean registerAccount(UserTableDTO account) 
            throws NamingException, SQLException{
        
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        
        try{
            String query = "INSERT INTO userTbl "
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            
            conn = DBHelper.getConnection();
            
            preStm = conn.prepareStatement(query);
            
            int stmIndex = 1;
            
            preStm.setString(stmIndex++, account.getEmail());
            preStm.setString(stmIndex++, account.getPassword());
            preStm.setString(stmIndex++, account.getNameF());
            preStm.setString(stmIndex++, account.getNameL());
            preStm.setInt(stmIndex++, AccountHelper.DEFAULT_ROLE);
            preStm.setInt(stmIndex++, DBHelper.DEFAULT_STATUS);
            
            int rowsAffected = preStm.executeUpdate();
            
            if(rowsAffected > 0)
                result = true;
        }
        finally{
            if (rs != null)
                rs.close();
            if (preStm != null)
                preStm.close();
            if (conn != null)
                conn.close();
        }
        
        return result;
    }
    
    public boolean checkDupeAccount(String accountName)
            throws NamingException, SQLException{
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        
        try{
            String query = "SELECT username "
                    + "FROM userTbl "
                    + "WHERE username = ? ";
            
            conn = DBHelper.getConnection();
            
            preStm = conn.prepareStatement(query);
            
            preStm.setString(1, accountName);
            
            rs = preStm.executeQuery();
            
            if(rs.next())
                result = true;
        }
        finally{
            if (rs != null)
                rs.close();
            if (preStm != null)
                preStm.close();
            if (conn != null)
                conn.close();
        }
        
        return result;
    }
    
    
    public UserTableDTO checkLogin(String username, String password)
            throws SQLException, ClassNotFoundException, NamingException {
        System.out.println("-atUserTableDAO-checkLogin");
        // 1. Connects DB
        Connection conn = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;

        UserTableDTO result = null;

        try {
            conn = DBHelper.getConnection();
            if (conn != null) {
                // 2. Creates Query
                String query = "SELECT username, password, "
                        + "role, nameFirst, nameLast "
                        + "FROM userTbl "
                        + "WHERE username = ? AND password = ? AND status = ?";

                // 3. Create Statement
                preStm = conn.prepareStatement(query);
                preStm.setString(1, username);
                preStm.setString(2, password);
                preStm.setInt(3, DBHelper.ACTIVE_STATUS);

                // 4. Query Data
                rs = preStm.executeQuery();

                // 5. Process Data
                if (rs.next()) {
                    UserTableDTO dto = new UserTableDTO();

                    dto.setUsername(rs.getString("username"));
                    dto.setRole(rs.getInt("role"));
                    dto.setNameF(rs.getString("nameFirst"));
                    dto.setNameL(rs.getString("nameLast"));

                    result = dto;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (preStm != null) {
                preStm.close();
            }
        }

        return result;
    }

}
