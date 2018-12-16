package domosapp.models;


public class Module {
    private String type;
    private String name;
    private String label;
    private String command;

    public Module(String type, String name, String label, String command) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
