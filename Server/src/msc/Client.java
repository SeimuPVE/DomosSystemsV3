package msc;

import modules.ModulePattern;
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
    private PrintStream writer;
    private BufferedReader reader;

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
        msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_new_client);
        msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_total_client + nb_clients);
    }

    @Override
    public void run() {
        try {
            // Get the command and execute it.
            String cmd = reader.readLine();

            for(ModulePattern module : modules)
                writer.print(module.exec(cmd));

            msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_command);

            // Disconnect the client because we don't need to stay connected.
            disconnect();
        }
        catch (Exception e) {
            msc.Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
        }
    }

    private void disconnect() throws IOException {
        if(socket != null) {
            socket.close();

            nb_clients--;
            msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_delete_client + nb_clients);
            msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_total_client + nb_clients);
        }
    }

    public PrintStream getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }
}
