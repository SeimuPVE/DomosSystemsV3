package network;

import clientManager.UserList;
import modules.ModulePattern;
import msc.ConfigReader;
import msc.Logger;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {
    private static int nb_clients = 0;
    private Socket socket;
    private PrintStream socketWriter;
    private BufferedReader socketReader;

    private ArrayList<ModulePattern> modules;

    public Client(Socket socket, ArrayList<ModulePattern> modules) throws IOException {
        // Create connection.
        this.socket = socket;
        socketWriter = new PrintStream(socket.getOutputStream());
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Import modules.
        this.modules = modules;
        for(ModulePattern module: modules)
            module.setClient(this);

        // Increase number of clients and log it.
        nb_clients++;

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 3) {
            msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_new_client);
            msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_total_client + nb_clients);
        }
    }

    @Override
    public void run() {
        try {
            // Get the command and execute it.
            UserList userList = Server.getUsers();
            Code code = new Code(socketReader.readLine());

            // If the option is activated, check if the user is valid.
            if(ConfigReader.readValue(CONF_CODES.authentication).equals(STRINGS.yes)) {
                if(userList.userAlreadyExists(code.getUsername())) {
                    if(!userList.isAValidUser(code.getUsername(), code.getPassword())) {
                        msc.Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.connection_error_wrong_password);
                        disconnect();
                        return;
                    }
                }
                else {
                    msc.Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.connection_error_wrong_username);
                    disconnect();
                    return;
                }
            }

            // Execute the commands.
            for(ModulePattern module : modules)
                for(String cmd : code.getCommandList()) {
                    socketWriter.println(module.exec(cmd));

                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
                        msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_command + cmd);
                }

            // Disconnect the client because we don't need to stay connected.
            disconnect();
        }
        catch (Exception e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                msc.Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.connection_error);

            try {
                disconnect();
            }
            catch (IOException e1) {
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    msc.Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
            }
        }
    }

    private void disconnect() throws IOException {
        if(socket != null) {
            socket.close();

            nb_clients--;

            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 3) {
                msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_delete_client);
                msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_total_client + nb_clients);
            }
        }
    }

    public void sendLogs(String log) {
        socketWriter.println(log);
    }

    public PrintStream getWriter() {
        return socketWriter;
    }

    public BufferedReader getSocketReader() {
        return socketReader;
    }
}
