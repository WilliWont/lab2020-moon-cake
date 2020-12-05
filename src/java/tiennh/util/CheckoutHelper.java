/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tiennh.caketable.CakeTableDAO;
import tiennh.caketable.CakeTableDTO;

public class CheckoutHelper implements Serializable {

    public static final int NAME_MAX_LEN = 64;

    public static final int ADDRLINE_MAX_LEN = 32;

    public static final int WARD_MAX_LEN = 32;

    public static final int DISTRICT_MAX_LEN = 32;

    public static final int CITY_MAX_LEN = 32;

    public static final int DEFAULT_PAYMENT_METHOD = 0;
    public static final int DEFAULT_PAYMENT_STATUS = 0;

    // PENDING! POSSIBLE OBSOLETE
    public static Map<Integer, Integer>
            checkQuantity(List<CakeTableDTO> cakeList) throws SQLException, NamingException {
        CakeTableDAO dao = new CakeTableDAO();

        Map<Integer, Integer> result = new HashMap();
        Map<Integer, Integer> cakes = dao.getProductQuantity();

        for (CakeTableDTO cake : cakeList) {
            int stock = cakes.get(cake.getId());
            if (stock < cake.getQuantity()) {
                result.put(cake.getId(), stock);
            }
        }

        if (result.isEmpty()) {
            return null;
        }

        return result;
    }
}
