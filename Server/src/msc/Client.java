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
        // Create connection.
        this.socket = socket;
        writer = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Import modules.
        this.modules = modules;
        for(ModulePattern module: modules)
            module.setClient(this);

        // Increase number of clients and log it.
        nb_clients++;
        LOGGER.log(Level.FINE, log_new_client);
        LOGGER.log(Level.FINE, log_total_client, nb_clients);
    }

    @Override
    public void run() {
        try {
            // Get the command and execute it.
            String cmd = reader.readLine();

            for(ModulePattern module : modules)
                writer.print(module.exec(cmd));

            LOGGER.log(Level.FINE, log_command, cmd);

            // Disconnect the client because we don't need to stay connected.
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

    public PrintStream getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
