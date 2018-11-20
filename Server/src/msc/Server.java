package msc;

import clientManager.UserList;
import clientManager.UserLoader;
import modules.*;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private String filepath = ConfigReader.readValue(CONF_CODES.users_filepath);
    private Socket socket = null;
    private int port;

    private ArrayList<ModulePattern> moduleList = new ArrayList<>();
    private UserList users;

    public void run(String[] argv) {
        try {
            // Initialize server.
            port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.server_port));

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(InetAddress.getLocalHost());

            // Prepare logs.
            LOGGER.log(Level.FINE, STRINGS.log_server_up);

            // Load users.
            users = UserLoader.loadUsers(filepath);

            // Load modules.
            moduleList = ModuleLoader.loadModules();

            // Loop to manage clients.
            while(true) {
                System.out.println("Server OK !"); // TODO : delete it and add unit tests.

                socket = serverSocket.accept();
                new Thread(new Client(socket, moduleList)).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // TODO : catch errors.
        }
        finally {
            try {
                if(socket != null)
                    socket.close();

                LOGGER.log(Level.FINE, STRINGS.log_server_closed);
            }
            catch (IOException e) {
                e.printStackTrace(); // TODO : catch errors.
            }
        }
    }

    private void loadUsers(String filepath) {
    }
}
