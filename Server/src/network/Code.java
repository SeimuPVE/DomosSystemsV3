package network;

import rsc.STRINGS;

import java.util.ArrayList;

public class Code {
    private String username;
    private String password;

    private ArrayList<String> command_list;

    // TODO : check wrong messages.
    public Code(String username, String password, ArrayList<String> command_list) {
        this.username = username;
        this.password = password;
        this.command_list = command_list;
    }

    public Code(String username, String password, String command_line) {
        int i;
        String[] commands = command_line.split(STRINGS.splitter_2);

        command_list = new ArrayList<>();

        for(i = 0; i < commands.length; i++)
            command_list.add(commands[i]);

        this.username = username;
        this.password = password;
    }

    public Code(String message) {
        String[] command_line;
        int i;

        command_list = new ArrayList<>();

        username = message.split(STRINGS.splitter_1)[0];
        password = message.split(STRINGS.splitter_1)[1];
        command_line = message.split(STRINGS.splitter_1)[2].split(STRINGS.splitter_2);

        for(i = 0; i < command_line.length; i++)
            command_list.add(command_line[i]);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getCommandList() {
        return command_list;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCommandList(ArrayList<String> command_list) {
        this.command_list = command_list;
    }

    public String getAll() {
        String all_line = username + password;

        for(String cmd : command_list)
            all_line += cmd;

        return all_line;
    }
}
