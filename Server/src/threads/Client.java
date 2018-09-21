package threads;

import modules.ModulePattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
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
        System.out.println("A new client is connected.");
        System.out.println("Total : " + nb_clients);
    }

    @Override
    public void run() {
        try {
            String cmd = reader.readLine();

            System.out.println("Command : " + cmd);
            for(ModulePattern module : modules)
                writer.print(module.exec(cmd));

            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() throws IOException {
        if(socket != null) {
            socket.close();

            nb_clients--;
            System.out.println("A client disconnected !");
            System.out.println("Total : " + nb_clients);
        }
    }
}
