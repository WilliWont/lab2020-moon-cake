/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Form;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import tiennh.google.GoogleObject;


public class GoogleHelper {

    public static String getToken(final String code) throws ClientProtocolException, IOException {
        String response = Request.Post(OAuthConstant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", OAuthConstant.GOOGLE_CLIENT_ID)
                        .add("client_secret", OAuthConstant.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", OAuthConstant.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", OAuthConstant.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GoogleObject getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = OAuthConstant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GoogleObject googlePojo = new Gson().fromJson(response, GoogleObject.class);
        System.out.println("POJO:"+googlePojo.getGiven_name());
        System.out.println("POJO:"+googlePojo.getEmail());
                System.out.println("POJO:"+googlePojo.getName());

        return googlePojo;
    }

}
