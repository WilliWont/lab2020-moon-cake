/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import tiennh.caketable.CakeTableDTO;

public class CategoryObject implements Serializable {

    private List<CakeTableDTO> cakeList;
    private final int categoryID;
    private final String categoryName;

    public CategoryObject(int id, String name) {
        this.categoryID = id;
        this.categoryName = name;
    }

    public List<CakeTableDTO> getCakeList() {
        return cakeList;
    }

    public void addCake(CakeTableDTO cake) {
        if (cakeList == null) {
            cakeList = new ArrayList();
        }

        cakeList.add(cake);
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryCakeAmount() {
        return cakeList.size();
    }
}
