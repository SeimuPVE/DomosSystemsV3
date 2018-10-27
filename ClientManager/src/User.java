import java.io.Serializable;


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
        System.out.println(password + "_HASH_TODO");
        return password + "_HASH_TODO"; // TODO : hash the password.
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
