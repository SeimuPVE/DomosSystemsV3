package domosapp.models;


public class User {
    private String pseudo;
    private String password;

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }
}
