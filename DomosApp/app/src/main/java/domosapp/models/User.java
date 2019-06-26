package domosapp.models;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import domosapp.adapters.SettingsDatabaseAdapter;
import domosapp.utils.Constants;


public class User {
    private String pseudo;
    private String password;

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        setPassword(password);
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password; // We don't need to hash the password, we hash it as soon as we take it form the UI.
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public static String hashPassword(String password) {
        String hashed_password = null;
        String salt = SettingsDatabaseAdapter.getSettings().getSalt();

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
            Log.d(Constants.api_tag, e.getMessage());
        }

        return hashed_password;
    }
}
