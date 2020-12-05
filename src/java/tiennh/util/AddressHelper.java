/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import com.google.gson.JsonObject;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import tiennh.addresstable.AddressTableDTO;
import tiennh.usertable.UserTableDTO;

public class AddressHelper implements Serializable {

    public static final String DEFAULT_USER = "guest";

    public static final int NAME_LEN_MAX = 32;

    public static final int PHONE_LEN_MIN = 10;
    public static final int PHONE_LEN_MAX = 12;
    public static final String PHONE_FORMAT = "^[0-9]*$";

    public static final int ADDR_LINE_MAX = 128;
    public static final int ADDR_WARD_MAX = 64;
    public static final int ADDR_DISTRICT_MAX = 64;
    public static final int ADDR_CITY_MAX = 64;

    public static final String EMPTY_ERROR = "empty field";

    public static AddressTableDTO getAddressParameter(HttpServletRequest req) {
        int addrID;
        try {
            addrID = Integer.parseInt(req.getParameter("txtAddrID"));
        } catch (Throwable t) {
            addrID = 0;
        }

        AddressTableDTO addr = new AddressTableDTO();

        if (addrID == 0) {
            System.out.println("no address");
            addr.setNameF(DBHelper.filterHTML(req.getParameter("txtNameF")));
            addr.setNameL(DBHelper.filterHTML(req.getParameter("txtNameL")));
            addr.setAddrLine(DBHelper.filterHTML(req.getParameter("txtAddrLine")));
            addr.setWard(DBHelper.filterHTML(req.getParameter("txtAddrWard")));
            addr.setDistrict(DBHelper.filterHTML(req.getParameter("txtAddrDistrict")));
            addr.setCity(DBHelper.filterHTML(req.getParameter("txtAddrCity")));
            addr.setPhone(DBHelper.filterHTML(req.getParameter("txtAddrPhone")));

            HttpSession ses = req.getSession();
            String username = DEFAULT_USER;
            UserTableDTO user = (UserTableDTO) ses.getAttribute("SES_USER");
            if (user != null) {
                username = user.getUsername();
            }

            addr.setUsername(username);
        } else {
            System.out.println("got address: " + addrID);
            addr.setId(addrID);
        }

        return addr;
    }

    public static JsonObject validateAddress(AddressTableDTO address) {
        boolean hasError = false;
        JsonObject result = new JsonObject();

        hasError |= validateField("txtAddrLine", result, ADDR_LINE_MAX,
                address.getAddrLine());
        hasError |= validateField("txtAddrCity", result, ADDR_CITY_MAX,
                address.getCity());
        hasError |= validateField("txtAddrDistrict", result, ADDR_DISTRICT_MAX,
                address.getDistrict());
        hasError |= validateField("txtAddrWard", result, ADDR_WARD_MAX,
                address.getWard());
        hasError |= validateField("txtNameF", result, NAME_LEN_MAX,
                address.getNameF());
        hasError |= validateField("txtNameL", result, NAME_LEN_MAX,
                address.getNameL());
        hasError |= validateField("txtAddrPhone", result, PHONE_LEN_MAX, PHONE_LEN_MIN,
                address.getPhone(), PHONE_FORMAT);

        if (!hasError) {
            result = null;
        }

        return result;
    }

    private static Boolean validateField(String field, JsonObject result,
            int maxValue, String value) {
        Boolean hasError = false;
        if (value == null || (value = value.trim()).length() == 0) {
            hasError = true;
            result.addProperty(field, EMPTY_ERROR);
        } else if (value.length() > maxValue) {
            hasError = true;
            result.addProperty(field, maxValue + " characters maximum");
        }
        if (!hasError) {
            result.addProperty(field, "");
            return false;
        } else {
            return true;
        }
    }

    private static Boolean validateField(String field, JsonObject result,
            int maxValue, int minValue, String value, String regex) {
        Boolean hasError = false;
        if (value == null || (value = value.trim()).length() == 0) {
            hasError = true;
            result.addProperty(field, EMPTY_ERROR);
        } else if (value.length() > maxValue) {
            hasError = true;
            result.addProperty(field, maxValue + " characters maximum");
        } else if (value.length() < minValue) {
            hasError = true;
            result.addProperty(field, minValue + " characters minimum");
        } else if (!value.matches(regex)) {
            hasError = true;
            result.addProperty(field, "invalid format");
        }
        if (!hasError) {
            result.addProperty(field, "");
            return false;
        } else {
            return true;
        }
    }
}
