public class User {
    private String login;
    private String hashed_password;

    public User(String login, String hashed_password) {
        this.login = login;
        this.hashed_password = hashed_password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashed_password() {
        return hashed_password;
    }

    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
    }

    public boolean isAdmin() {
        if(login.equals(STRINGS.admin_name))
            return true;
        return false;
    }
}
