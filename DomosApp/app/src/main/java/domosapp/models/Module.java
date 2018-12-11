package domosapp.models;

public class Module {
    private String type;
    private String ip;
    private String port;
    private String label;
    private String description;

    public Module(String type, String ip, String port, String label, String description) {
        this.type = type;
        this.ip = ip;
        this.port = port;
        this.label = label;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
