package modules;

import msc.ConfigReader;
import msc.Logger;
import rsc.CODES;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class LedStrip extends ModulePattern {
    private Socket socket = null;
    private PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private boolean isOn = false;

    public LedStrip() {
        ip = ConfigReader.readValue(CONF_CODES.led_strip_ip);
        port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.led_strip_port));
        timeout = Integer.parseInt(ConfigReader.readValue(CONF_CODES.led_strip_timeout));
    }

    public String exec(String command) {
        if(command.equals(CODES.LED_STRIP_REVERSE))
            reverseLights();
        else if(command.equals(CODES.LED_STRIP_ON))
            turnOn();
        else if(command.equals(CODES.LED_STRIP_OFF))
            turnOff();
        else if(command.equals(CODES.LED_STRIP_GREEN))
            turnGreen();
        else if(command.equals(CODES.LED_STRIP_CYAN))
            turnCyan();
        else if(command.equals(CODES.LED_STRIP_MAGENTA))
            turnMagenta();

        return STRINGS.log_error + STRINGS.unreconnized_command;
    }

    public void sendByteCode(byte[] code) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);

            if(socket.isConnected()) {
                writer = new PrintStream(socket.getOutputStream());
                writer.write(code);
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
        byte[] code_on = new byte[] {(byte)0x71, (byte)0x23, (byte)0x0f, (byte)0xa3};
        sendByteCode(code_on);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_on);
            clientLogSuccess(STRINGS.log_led_strip_on);
        }
    }

    public void turnOff() {
        byte[] code_off = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_off);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_off);
            clientLogSuccess(STRINGS.log_led_strip_off);
        }
    }

    public void turnGreen() {
        byte[] code_green = new byte[] {(byte)0x31, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:ff:00:00:00:0f:3e
        sendByteCode(code_green);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_green);
            clientLogSuccess(STRINGS.log_led_strip_green);
        }
    }

    public void turnCyan() {
        byte[] code_cyan = new byte[] {(byte)0x31, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:00:ff:ff:00:00:0f:3e
        sendByteCode(code_cyan);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_cyan);
            clientLogSuccess(STRINGS.log_led_strip_cyan);
        }
    }

    public void turnMagenta() {
        byte[] code_magenta = new byte[] {(byte)0x31, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:00:ff:00:00:0f:3e
        sendByteCode(code_magenta);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_magenta);
            clientLogSuccess(STRINGS.log_led_strip_magenta);
        }
    }

    public void turnWhite() {
        byte[] code_white = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_white);

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2) {
            Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_led_strip_white);
            clientLogSuccess(STRINGS.log_led_strip_white);
        }
    }
}
