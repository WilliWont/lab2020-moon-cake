/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiennh.util;

import java.io.Serializable;
import org.apache.commons.codec.digest.DigestUtils;

public class AccountHelper implements Serializable {

    public static final int COOKIE_AGE = 60 * 10; // 10 minutes

    public static final int PASSWORD_MIN_LEN = 8;
    public static final int PASSWORD_MAX_LEN = 16;

    public static final int FULLNAME_MIN_LEN = 1;
    public static final int FULLNAME_MAX_LEN = 64;

    public static final int USERNAME_MIN_LEN = 8;
    public static final int USERNAME_MAX_LEN = 16;

    public static final String USERNAME_REGEX = "^[A-Za-z0-9]*$";
    public static final String PASSWORD_REGEX = "^[A-Za-z0-9]*$";

    public static final int ADMIN_ROLE = 0;
    public static final int MEMBER_ROLE = 1;
    public static final int GUEST_ROLE = 2;
    public static final int THIRD_PARTY_ROLE = 3;
    
    public static final int NAME_MIN_LEN = 0;
    public static final int NAME_MAX_LEN = 64;

    public static final int DEFAULT_ROLE = 1;

    // Uses Apache Common Codec 1.15
    public static String hashPassword(String password) {
        if (password != null) {
            return DigestUtils.sha256Hex(password);
        } else {
            return null;
        }
    }
    
    public static boolean validateEmail(String email){
        return true;
//        return EmailValidator.getInstance().isValid(email);
    }
}
