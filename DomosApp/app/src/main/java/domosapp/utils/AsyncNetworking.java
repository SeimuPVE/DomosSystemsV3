package domosapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import domosapp.adapters.UserDatabaseAdapter;


public class AsyncNetworking extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String ip;
    private int port;
    private String message;
    private String return_message;

    public AsyncNetworking(Context context, String ip, int port, String message) {
        this.context = context;
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Socket socket = new Socket(ip, port);
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send the message.
            if(UserDatabaseAdapter.getUser() != null)
                printStream.println(UserDatabaseAdapter.getUser().getPseudo() + STRINGS.code_separator + UserDatabaseAdapter.getUser().getPassword() + STRINGS.code_separator + message);
            else
                printStream.println(STRINGS.null_user + message);

            // Get the return value.
            return_message = reader.readLine();

            printStream.close();
            socket.close();
        }
        catch (IOException e) {
            return_message = e.getMessage();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(context, return_message, Toast.LENGTH_LONG).show();
    }
}
