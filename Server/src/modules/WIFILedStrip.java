package modules;

import msc.ConfigReader;
import msc.Logger;
import rsc.COMMANDS;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class WIFILedStrip extends ModulePattern {
    private Socket socket = null;
    private PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private boolean isOn = false;

    public WIFILedStrip() {
        ip = ConfigReader.readValue(CONF_CODES.wifi_led_strip_ip);
        port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.wifi_led_strip_port));
        timeout = Integer.parseInt(ConfigReader.readValue(CONF_CODES.wifi_led_strip_timeout));
    }

    public String exec(String command) {
        if(command.equals(COMMANDS.WIFI_LED_STRIP_REVERSE))
            reverseLights();
        else if(command.equals(COMMANDS.WIFI_LED_STRIP_ON))
            turnOn();
        else if(command.equals(COMMANDS.WIFI_LED_STRIP_OFF))
            turnOff();

        return STRINGS.log_error + STRINGS.unrecognized_command;
    }

    public void sendCommand(char command) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);

            if(socket.isConnected()) {
                writer = new PrintStream(socket.getOutputStream());
                writer.print(command);
                writer.print(COMMANDS.SENSOR_EXIT);
            }
        }
        catch (SocketTimeoutException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 1) {
                Logger.log(Logger.LevelWARNING, this.getClass().getName(), STRINGS.log_led_strip_timeout);
                clientLogError(STRINGS.log_led_strip_timeout);
            }
        }
        catch (IOException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0) {
                Logger.log(Logger.LevelSEVERE, ModuleLoader.class.getName(), e.getMessage());
                clientLogError(e.getMessage());
            }
        }
        finally {
            try {
                if(writer != null)
                    writer.close();

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

    public void reverseLights() {
        if(isOn) {
            turnOff();
            isOn = false;
        }
        else {
            turnOn();
            isOn = true;
        }
    }

    public void turnOn() {
        char code_on = 'O';
        sendCommand(code_on);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_on);
            clientLogSuccess(STRINGS.log_led_strip_on);
        }
    }

    public void turnOff() {
        char code_off = 'F';
        sendCommand(code_off);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_off);
            clientLogSuccess(STRINGS.log_led_strip_off);
        }
    }
}
