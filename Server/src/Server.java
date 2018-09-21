import modules.EnvironmentSensors;
import modules.LedStrip;
import modules.Lights;
import modules.ModulePattern;
import msc.Client;
import automaters.SensorsAutomater;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static msc.Strings.log_server_up;


public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private static final int port = 5433; // TODO : put it in a configuration file.

    private static ArrayList<ModulePattern> moduleList = new ArrayList<>();
    private static Socket socket = null;

    public static void main(String[] argv) {
        try {
            // Add modules.
            moduleList.add(new Lights());
            moduleList.add(new EnvironmentSensors());
            moduleList.add(new LedStrip());

            // Initialize server.
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println(InetAddress.getLocalHost());

            moduleList.get(0).exec("L_BACK_OFF");
            moduleList.get(0).exec("L_FRONT_OFF");

            // Launch threads to automate actions.
            new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

            LOGGER.log(Level.FINE, log_server_up);

            // Loop to manage clients.
            while (true) {
                socket = serverSocket.accept();
                new Thread(new Client(socket, moduleList)).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
