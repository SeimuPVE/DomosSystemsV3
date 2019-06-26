package domosapp.models;


public class Settings {
    private String ip;
    private int port;
    private String salt;

    public Settings(String ip, int port, String salt) {
        this.ip = ip;
        this.port = port;
        this.salt = salt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
