package network;

import clientManager.UserList;
import clientManager.UserLoader;
import modules.*;
import msc.ConfigReader;
import msc.Logger;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private String filepath = ConfigReader.readValue(CONF_CODES.users_filepath);
    private Socket socket = null;
    private int port;

    private ArrayList<ModulePattern> moduleList = new ArrayList<>();
    private static UserList users;

    public void run(String[] argv) {
        try {
            // Initialize server.
            port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.server_port));

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(InetAddress.getLocalHost());

            // Prepare logs.
            Logger.setFilepath(ConfigReader.readValue(CONF_CODES.log_path));

            // Load users.
            users = UserLoader.loadUsers(filepath);

            // Load modules.
            moduleList = ModuleLoader.loadModules();

            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
                Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_server_up);

            // Loop to manage clients.
            while(true) {
                socket = serverSocket.accept();
                new Thread(new Client(socket, moduleList)).start();
            }
        }
        catch (Exception e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
        }
        finally {
            try {
                if(socket != null)
                    socket.close();

                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    Logger.log(Logger.LevelSEVERE, this.getClass().getName(), STRINGS.log_server_closed);
            }
            catch (IOException e) {
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
            }
        }
    }

    public static UserList getUsers() {
        return users;
    }
}
