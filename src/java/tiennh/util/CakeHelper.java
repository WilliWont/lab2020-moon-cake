/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import tiennh.caketable.CakeTableDTO;

public class CakeHelper {

    private static String IMAGE_PATH = null;
    public static final String IMAGE_TAG_PATH = "img/cake/";
    public static final String IMAGE_EXTENSION = ".png";
    private static final int UPLOAD_DELAY = 1000 * 0; // 2 seconds

    public static final int IMAGE_MAX_SIZE = 5;
    public static final int IMAGE_MAX_SIZE_VALUE = 1000000 * IMAGE_MAX_SIZE;
    // 1 MB = 1000000 bytes

    public static final int NAME_MIN_LEN = 0;
    public static final int NAME_MAX_LEN = 32;

    public static final int MIN_QUANTITY = 0;

    public static int MIN_PRICE_RANGE;
    public static int MAX_PRICE_RANGE;
    public static int MIN_PRICE_RANGE_IN_STOCK;
    public static int MAX_PRICE_RANGE_IN_STOCK;

    public static void setPriceRange(int min, int max) {
        MIN_PRICE_RANGE = min;
        MAX_PRICE_RANGE = max;
    }

    public static void setPriceRangeInStock(int min, int max) {
        MIN_PRICE_RANGE_IN_STOCK = min;
        MAX_PRICE_RANGE_IN_STOCK = max;
    }

    public static void setImagePath(String path) {
        CakeHelper.IMAGE_PATH = path;
    }

    public static String getImagePath() {
        return CakeHelper.IMAGE_PATH;
    }

    public static boolean uploadImage(String imageId, Part image)
            throws IOException, InterruptedException {
        System.out.println("at uploadImage");
        StringBuilder address = new StringBuilder(CakeHelper.IMAGE_PATH);

        address.append(imageId);
        address.append(IMAGE_EXTENSION);

        image.write(address.toString());
        Thread.sleep(UPLOAD_DELAY);
        return true;
    }

    public static JsonArray listToJson(List<CakeTableDTO> cakeList) {
        System.out.println("at listToJson");
        JsonArray toReturn = null;

        if (cakeList != null) {
            for (CakeTableDTO dto : cakeList) {
                if (toReturn == null || toReturn.size() == 0) {
                    toReturn = new JsonArray();
                }

                JsonObject cake = new JsonObject();

                cake.addProperty("id", dto.getId());
                cake.addProperty("name", dto.getName());
                cake.addProperty("quantity", dto.getQuantity());
                cake.addProperty("image", dto.getImage());
                cake.addProperty("imagePath", dto.getImagePath());
                cake.addProperty("price", dto.getPrice());
                cake.addProperty("description", dto.getDescription());
                cake.addProperty("cateID", dto.getCategoryID());
                cake.addProperty("cateName", dto.getCategoryName());

                toReturn.add(cake);
            }
        }

        return toReturn;
    }

    public static JsonObject cakeToJson(CakeTableDTO cake) {
        JsonObject toReturn = new JsonObject();

        toReturn.addProperty("id", cake.getId().toString());
        toReturn.addProperty("name", cake.getName());
        toReturn.addProperty("desc", cake.getDescription());
        toReturn.addProperty("cateID", cake.getCategoryID());
        toReturn.addProperty("price", cake.getPrice());
        toReturn.addProperty("quantity", cake.getQuantityStock());
        toReturn.addProperty("createDate", cake.getCreateDate().toString());
        toReturn.addProperty("expireDate", cake.getExpireDate().toString());
        toReturn.addProperty("img", cake.getImagePath());
        return toReturn;
    }

    public static Integer getCateIDParam(HttpServletRequest req) {
        String txtCateID = req.getParameter("txtCateID");
        Integer cateID;
        try {
            cateID = Integer.parseInt(txtCateID);
        } catch (Throwable t) {
            cateID = null;
        }
        return cateID;
    }

    public static Float getFloatParam(HttpServletRequest req, String param) {
        String txtCateID = req.getParameter(param);
        Float cateID;
        try {
            cateID = Float.parseFloat(txtCateID);
        } catch (Throwable t) {
            cateID = null;
        }
        return cateID;
    }

    public static CakeTableDTO getCakeParameters(HttpServletRequest req) throws ParseException {

        String txtCakeName = DBHelper.filterHTML(req.getParameter("txtCakeName"));
        String txtCateID = DBHelper.filterHTML(req.getParameter("txtCateID"));
        String txtCakePrice = DBHelper.filterHTML(req.getParameter("txtCakePrice"));
        String txtCakeQuantity = DBHelper.filterHTML(req.getParameter("txtCakeQuantity"));
        String txtCakeDesc = DBHelper.filterHTML(req.getParameter("txtCakeDescription"));
        String txtCreateDate = DBHelper.filterHTML(req.getParameter("txtCakeCreateDate"));
        String txtExpireDate = DBHelper.filterHTML(req.getParameter("txtCakeExpireDate"));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        CakeTableDTO cake = new CakeTableDTO();
        cake.setName(txtCakeName);
        cake.setDescription(txtCakeDesc);
        cake.setCategoryID(Integer.parseInt(txtCateID));
        cake.setPrice(Float.parseFloat(txtCakePrice));
        cake.setQuantity(Integer.parseInt(txtCakeQuantity));

        if (txtCreateDate != null && txtCreateDate.length() > 0) {
            cake.setCreateDate(df.parse(txtCreateDate));
        }

        if (txtExpireDate != null && txtExpireDate.length() > 0) {
            cake.setExpireDate(df.parse(txtExpireDate));
        }

        return cake;
    }
}
