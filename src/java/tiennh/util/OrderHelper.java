/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderHelper implements Serializable {

    private Map<Integer, String> orderStatus = null;
    private Map<Integer, String> orderMethod = null;

    public OrderHelper() {
        orderStatus = new HashMap();
        orderStatus.put(0, "Pending");
        orderStatus.put(1, "Paid");

        orderMethod = new HashMap();
        orderMethod.put(0, "Cash On Delivery");
        orderMethod.put(1, "Paypal");
    }

    public Map<Integer, String> getOrderStatus() {
        return orderStatus;
    }

    public Map<Integer, String> getOrderMethod() {
        return orderMethod;
    }

    public String getMethod(int id) {
        return orderMethod.get(id);
    }

    public String getStatus(int id) {
        return orderStatus.get(id);
    }
}
