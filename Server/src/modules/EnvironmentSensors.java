package modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EnvironmentSensors extends ModulePattern {
    private static Socket socket = null;
    private static BufferedReader reader = null;
    private static PrintStream writer = null;

    // TODO : put it in a configuration file.
    private static final String ip = "192.168.0.119";
    private static final int port = 8266;
    private static final int timeout = 5000;

    private double temperature;
    private double humidity;
    private double luminosity;

    public EnvironmentSensors() {
        updateSensors();
    }

    public void updateSensors() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);

            if(socket.isConnected()) {
                writer = new PrintStream(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer.print('T');
                temperature = Double.parseDouble(reader.readLine());

                writer.print('H');
                humidity = Double.parseDouble(reader.readLine());

                writer.print('L');
                luminosity = 100 * Double.parseDouble(reader.readLine()) / 1024.0;

                writer.print('E');
                System.out.println("Environment sensor updated.");
            }
        }
        catch (SocketTimeoutException e) {
            System.out.println("Timeout exception with environment sensors !");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(writer != null)
                    writer.close();

                if(reader != null)
                    reader.close();

                if(socket != null)
                    socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String exec(String command) {
        // TODO : put those String in an external file.
        if(command.equals("GET_HUMIDITY"))
            return getHumidity();
        else if(command.equals("GET_LUMINOSITY"))
            return getLuminosity();
        else if(command.equals("GET_TEMPERATURE"))
            return getTemperature();
        else
            return "Error.";
    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public String getHumidity() {
        return humidity + "%";
    }

    public String getLuminosity() {
        NumberFormat nf = new DecimalFormat("##.#");
        return nf.format(luminosity) + "%";
    }
}
