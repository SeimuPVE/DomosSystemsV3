package msc;

import automaters.SensorsAutomater;
import clientManager.ClientManager;
import clientManager.UserList;
import clientManager.UserSaverLoader;
import modules.*;
import rsc.CODES;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.File;
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
            loadUsers(filepath);

            // Load modules.
            loadModules();

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

                LOGGER.log(Level.FINE, STRINGS.log_server_closed);
            }
            catch (IOException e) {
                e.printStackTrace(); // TODO : catch errors.
            }
        }
    }

    private void loadUsers(String filepath) {
        File file = new File(filepath);

        // Load users.
        if(file.exists() && !file.isDirectory())
            users = UserSaverLoader.load();
        else
            users = new UserList();

        // If there is not any admin, add it and load again.
        if(!users.adminExists()) {
            System.out.println(STRINGS.first_launch);
            ClientManager.run(null);
        }

    }

    private void loadModules() {
        if(ConfigReader.readValue("lights").equals("on")) {
            moduleList.add(new Lights());

            // TODO : move initialisations on a config file.
            moduleList.get(0).exec(CODES.LIGHT_OFF + "_0");
            moduleList.get(0).exec(CODES.LIGHT_OFF + "_1");

            System.out.println("Lights loaded.");
        }
        if(ConfigReader.readValue("environment_sensors").equals("on")) {
            moduleList.add(new EnvironmentSensors());

            new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

            System.out.println("Environment sensors loaded.");
        }
        if(ConfigReader.readValue("led_strip").equals("on"))
            moduleList.add(new LedStrip());

            System.out.println("Led strip loaded.");
    }
}
