package modules;

import msc.ConfigReader;
import msc.Logger;
import rsc.CODES;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;


public class EnvironmentSensors extends ModulePattern {
    private Socket socket = null;
    private BufferedReader reader = null;
    private PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private double temperature;
    private double humidity;
    private double luminosity;

    public EnvironmentSensors() {
        ip = ConfigReader.readValue(CONF_CODES.environment_sensor_ip);
        port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.environment_sensor_port));
        timeout = Integer.parseInt(ConfigReader.readValue(CONF_CODES.environment_sensor_timeout));

        updateSensors();
    }

    public void updateSensors() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);

            if(socket.isConnected()) {
                writer = new PrintStream(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                writer.print(CODES.SENSOR_TEMPERATURE);
                temperature = Double.parseDouble(reader.readLine());

                writer.print(CODES.SENSOR_HUMIDITY);
                humidity = Double.parseDouble(reader.readLine());

                writer.print(CODES.SENSOR_LUMINOSITY);
                luminosity = 100 * Double.parseDouble(reader.readLine()) / 1024.0;

                writer.print(CODES.SENSOR_EXIT);
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
                    Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_environment_updated);
            }
        }
        catch (SocketTimeoutException e) {
            // Log it and alert user.
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 1) {
                Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.log_environment_timeout);
                clientLogError(STRINGS.log_environment_timeout);
            }

            // Wait and try again.
            try {
                TimeUnit.SECONDS.sleep(30);
                updateSensors();
            }
            catch (InterruptedException e1) {
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0) {
                    Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e1.getMessage());
                    clientLogError(e1.getMessage());
                }
            }
        }
        catch (NoRouteToHostException e) {
            // Log it and alert user.
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 1) {
                Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.log_environment_no_route_to_host);
                clientLogError(STRINGS.log_environment_no_route_to_host);
            }

            // Wait and try again.
        }
        catch (IOException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0) {
                Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
                clientLogError(e.getMessage());
            }
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
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0) {
                    Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
                    clientLogError(e.getMessage());
                }
            }
        }
    }

    public String exec(String command) {
        if(command.equals(CODES.GET_HUMIDITY))
            return getHumidity();
        else if(command.equals(CODES.GET_LUMINOSITY))
            return getLuminosity();
        else if(command.equals(CODES.GET_TEMPERATURE))
            return getTemperature();

        return STRINGS.log_error + STRINGS.unrecognized_command;
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
