package clientManager;

import msc.ConfigReader;
import msc.Logger;
import rsc.CONF_CODES;
import rsc.STRINGS;

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
        String salt = ConfigReader.readValue(CONF_CODES.salt);

        int i;
        byte[] hash;
        String hex;
        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
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
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                Logger.log(Logger.LevelSEVERE, User.class.getName(), e.getMessage());
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
