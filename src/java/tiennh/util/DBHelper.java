/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DBHelper implements Serializable {

    public static final int INACTIVE_STATUS = 0;
    public static final int ACTIVE_STATUS = 1;
    public static final int DEFAULT_STATUS = 1;

    public static final int PER_PAGE = 8;

    public static Connection getConnection() throws NamingException, SQLException {
        Context ctx = new InitialContext();

        Context cnCtx = (Context) ctx.lookup("java:comp/env");

        DataSource source = (DataSource) cnCtx.lookup("labDB");

        Connection conn = (Connection) source.getConnection();

        return conn;
    }

    public static String filterHTML(String input) {
        System.out.println("filteringHTML");
        if (input != null) {
            System.out.println("not null");
            return input.replaceAll("\\<.*?\\>", "");
        } else {
            System.out.println("null");
            return null;
        }
    }

}
