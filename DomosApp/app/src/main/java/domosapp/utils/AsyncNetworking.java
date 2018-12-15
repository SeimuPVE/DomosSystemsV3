package domosapp.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class AsyncNetworking extends AsyncTask<Void, Void, Void> {
    private String ip;
    private int port;
    private String message;

    public AsyncNetworking(String ip, int port, String message) {
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(ip, port);
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            printStream.println(message);

            // TODO : add the possibility to get a return.

            printStream.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
