package msc;

import automaters.SensorsAutomater;
import clientManager.ClientManager;
import clientManager.STRINGS;
import clientManager.UserList;
import clientManager.UserSaverLoader;
import modules.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rsc.STRINGS.*;


public class Server {
    private final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private String filepath = STRINGS.userfile; // TODO : move it into config file.

    private Socket socket = null;
    private int port;

    private ArrayList<ModulePattern> moduleList = new ArrayList<>();
    private UserList users;

    public void run(String[] argv) {
        try {
            // Prepare user list.
            prepareUserList(filepath);

            // Add modules.
            moduleList.add(new Lights());
            moduleList.add(new EnvironmentSensors());
            moduleList.add(new LedStrip());

            // Initialize server.
            port = Integer.parseInt(ConfigReader.readValue("server_port"));

            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(InetAddress.getLocalHost());

            moduleList.get(0).exec(CODES.L_BACK_OFF);
            moduleList.get(0).exec(CODES.L_FRONT_OFF);

            // Launch threads to automate actions.
            new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

            LOGGER.log(Level.FINE, log_server_up);

            // Loop to manage clients.
            while(true) {
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

                LOGGER.log(Level.FINE, log_server_closed);
            }
            catch (IOException e) {
                e.printStackTrace(); // TODO : catch errors.
            }
        }
    }

    private void prepareUserList(String filepath) {
        File file = new File(filepath);

        // Load users.
        if(file.exists() && !file.isDirectory())
            users = UserSaverLoader.load();
        else
            users = new UserList();

        // If there is not any admin, add it and load again.
        if(!users.adminExists()) {
            System.out.println(first_launch); // TODO : from string, be more clear.
            ClientManager.run(null);
        }

    }
}
