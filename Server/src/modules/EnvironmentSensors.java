package modules;

import msc.ConfigReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import static msc.Strings.log_environment_error;
import static msc.Strings.log_environment_timeout;
import static msc.Strings.log_environment_updated;


public class EnvironmentSensors extends ModulePattern {
    private static final Logger LOGGER = Logger.getLogger(EnvironmentSensors.class.getName());

    private static Socket socket = null;
    private static BufferedReader reader = null;
    private static PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private double temperature;
    private double humidity;
    private double luminosity;

    public EnvironmentSensors() {
        ip = ConfigReader.readValue("environment_sensor_ip");
        port = Integer.parseInt(ConfigReader.readValue("environment_sensor_port"));
        timeout = Integer.parseInt(ConfigReader.readValue("environment_sensor_timeout"));

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
                LOGGER.log(Level.FINE, log_environment_updated);
            }
        }
        catch (SocketTimeoutException e) {
            LOGGER.log(Level.WARNING, log_environment_timeout);
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
        if(command.equals("GET_HUMIDITY"))
            return getHumidity();
        else if(command.equals("GET_LUMINOSITY"))
            return getLuminosity();
        else if(command.equals("GET_TEMPERATURE"))
            return getTemperature();
        else {
            LOGGER.log(Level.WARNING, log_environment_error);
            return log_environment_error;
        }
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
