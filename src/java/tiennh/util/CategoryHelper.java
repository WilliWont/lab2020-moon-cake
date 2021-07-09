/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tiennh.caketable.CakeTableDTO;
import tiennh.category.CategoryObject;

public class CategoryHelper implements Serializable {

    // TODO: make initialization from DB if have time
    private final Map<Integer, String> cateList;

    public CategoryHelper() {
        cateList = new HashMap();
        cateList.put(0, "Bread");
        cateList.put(1, "Sweet");
        cateList.put(2, "Candy");
        cateList.put(3, "Cake");
    }

    public String getCategoryName(int id) {
        return cateList.get(id);
    }

    public List<CategoryObject> categorizeCakes(List<CakeTableDTO> productList) {
        List<CategoryObject> cakeList = new LinkedList();
        if (productList != null) {
            Integer previousCate = null;
            CategoryObject currentCateObj = null;
            for (CakeTableDTO cake : productList) {
                Integer currentCate = cake.getCategoryID();
                System.out.println("cake:" + cake.getId() + ", current: " + currentCate + ", prev: " + previousCate);
                System.out.println("-----------");
                if (currentCate != null) {
                    
                    boolean hasAdded = false;
                    
                    if (!currentCate.equals(previousCate)) {
                        System.out.println("not Equal");
                        
                        if (currentCateObj != null) {
                            System.out.println("new Category");
                            
                            cakeList.add(currentCateObj);
                        }

                        currentCateObj = new CategoryObject(currentCate, cake.getCategoryName());

                        currentCateObj.addCake(cake);
                        hasAdded = true;
                    }

                    if (!hasAdded) {
                        currentCateObj.addCake(cake);
                    }

                    previousCate = currentCate;
                }
            }
            
            cakeList.add(currentCateObj);   
        }

        Collections.sort(cakeList, new Comparator<CategoryObject>() {
            @Override
            public int compare(CategoryObject u1, CategoryObject u2) {
                return u2.getCategoryCakeAmount() - u1.getCategoryCakeAmount();
            }
        });
        
        return cakeList;
    }

}
