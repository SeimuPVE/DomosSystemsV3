package msc;

import modules.ModulePattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static msc.Strings.*;


public class Client implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private static int nb_clients = 0;
    private Socket socket;
    private PrintStream writer = null;
    private BufferedReader reader = null;

    private ArrayList<ModulePattern> modules;

    public Client(Socket socket, ArrayList<ModulePattern> modules) throws IOException {
        this.modules = modules;
        this.socket = socket;
        writer = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        nb_clients++;

        LOGGER.log(Level.FINE, log_new_client);
        LOGGER.log(Level.FINE, log_total_client, nb_clients);
    }

    @Override
    public void run() {
        try {
            String cmd = reader.readLine();

            LOGGER.log(Level.FINE, log_command, cmd);

            for(ModulePattern module : modules)
                writer.print(module.exec(cmd));

            disconnect();
        } catch (Exception e) {
            e.printStackTrace(); // TODO : catch errors.
        }
    }

    private void disconnect() throws IOException {
        if(socket != null) {
            socket.close();

            nb_clients--;
            LOGGER.log(Level.FINE, log_delete_client, nb_clients);
            LOGGER.log(Level.FINE, log_total_client, nb_clients);
        }
    }
}
