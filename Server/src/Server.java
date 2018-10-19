import modules.*;
import msc.Client;
import automaters.SensorsAutomater;
import msc.ConfigReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static msc.Strings.log_server_closed;
import static msc.Strings.log_server_up;


public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private static Socket socket = null;
    private static int port;

    private static ArrayList<ModulePattern> moduleList = new ArrayList<>();

    public static void main(String[] argv) {
        try {
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
}
