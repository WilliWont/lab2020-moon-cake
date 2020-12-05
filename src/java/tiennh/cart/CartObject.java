/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.cart;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import tiennh.caketable.CakeTableDTO;

public class CartObject implements Serializable {

    private List<CakeTableDTO> productList;
    private Float cartTotal;

    public CartObject() {
        this.cartTotal = new Float(0);
    }

    public List<CakeTableDTO> getProductList() {
        return productList;
    }

    public Float getCartTotal() {
        return cartTotal;
    }

    public CakeTableDTO getItem(int id) {
        if (productList != null) {
            for (CakeTableDTO cake : productList) {
                if (cake.getId() == id) {
                    return cake;
                }
            }
        }

        return null;
    }

    public void addItem(CakeTableDTO product) throws Exception {
//        System.out.println("CartObject-addItem: "+product.getProductID());
        int idToAdd = product.getId();

        // 1. Check cart existence
        if (this.productList == null) {
            productList = new ArrayList();
        }

        // 2. Check for item existence
        for (CakeTableDTO x : productList) {
            if (x.getId() == idToAdd) {
                System.out.println("stock: " + product.getQuantityStock());
                System.out.println("amount: " + product.getQuantity());
                if (product.getQuantityStock() > x.getQuantity()) {
                    x.incrementQuantity();
                    this.cartTotal += product.getPrice();
                    DecimalFormat df = new DecimalFormat("###.##");
                    this.cartTotal = Float.parseFloat(df.format(this.cartTotal));
                    System.out.println("CartObject-addItem-incremented");
                    return;
                } else {
                    throw new Exception();
                }
            }
        }

        // 3. Add new product if False at 2.
        System.out.println("CartObject-addItem-added");
        this.cartTotal += product.getPrice();
        productList.add(product);
        DecimalFormat df = new DecimalFormat("###.##");
        this.cartTotal = Float.parseFloat(df.format(this.cartTotal));
    }

    public void removeItem(int id) {
        // 1. Check if productList exist
        if (this.productList == null) {
            return;
        }

        // 2. Remove if item exists
        int listSize = productList.size();
        for (int i = 0; i < listSize; i++) {
            CakeTableDTO dto = productList.get(i);

            if (dto.getId() == id) {
                productList.remove(i);
                this.cartTotal
                        -= dto.getPrice() * dto.getQuantity();
                if (this.productList.isEmpty()) {
                    productList = null;
                    this.cartTotal = new Float(0);
                }

                DecimalFormat df = new DecimalFormat("###.##");
                this.cartTotal = Float.parseFloat(df.format(this.cartTotal));

                return;
            }
        }
    }

    public int updateItem(CakeTableDTO product) {
        // System.out.println("CartObject-addItem: "+product.getProductID());
        int idToUpdate = product.getId();
        if (product.getQuantity() == 0) {
            removeItem(idToUpdate);
            return 0;
        }

        for (CakeTableDTO x : productList) {
            if (x.getId() == idToUpdate) {
                x.setQuantity(product.getQuantity());
                calculateTotal();
                System.out.println("CartObject-updated");
                return x.getQuantity();
            }
        }
        return -1;
    }

    public void calculateTotal() {
        if (productList != null) {
            Float total = 0f;
            for (CakeTableDTO cake : productList) {
                total += cake.getPrice() * cake.getQuantity();
            }
            DecimalFormat df = new DecimalFormat("###.##");
            total = Float.parseFloat(df.format(total));
            this.cartTotal = total;
        }
    }
}
