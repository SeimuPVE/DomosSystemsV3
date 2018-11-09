package clientManager;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class User implements Serializable {
    private String login;
    private String hashed_password;


    public User(String login, String password) {
        this.login = login;
        hashAndSetPassword(password);
    }

    public boolean isAdmin() {
        if(login.equals(STRINGS.admin_name))
            return true;
        return false;
    }

    public static String hashPassword(String password) {
        String hashed_password = null;
        String salt = "SALT"; // TODO : take it from a config file.

        int i;
        byte[] hash;
        String hex;
        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            hashed_password = String.valueOf(digest.digest((password + salt).getBytes(StandardCharsets.UTF_8)));
            hash = digest.digest((password + salt).getBytes("UTF-8"));

            for(i = 0; i < hash.length; i++) {
                hex = Integer.toHexString(0xff & hash[i]);

                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            hashed_password = hexString.toString();
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return hashed_password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String hashAndSetPassword(String password) {
        this.hashed_password = hashPassword(password);
        return hashed_password;
    }

    public void setHashedPassword(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public String getLogin() {
        return login;
    }

    public String getHashedPassword() {
        return hashed_password;
    }
}
