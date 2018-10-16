package modules;

import msc.ConfigReader;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static modules.CODES.*;
import static msc.Strings.*;


public class LedStrip extends ModulePattern {
    private static final Logger LOGGER = Logger.getLogger(LedStrip.class.getName());

    private Socket socket = null;
    private PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private boolean isOn = false;

    public LedStrip() {
        ip = ConfigReader.readValue("led_strip_ip");
        port = Integer.parseInt(ConfigReader.readValue("led_strip_port"));
        timeout = Integer.parseInt(ConfigReader.readValue("led_strip_timeout"));
    }

    public String exec(String command) {
        if(command.equals(LED_STRIP_REVERSE))
            reverseLights();
        else if(command.equals(LED_STRIP_ON))
            turnOn();
        else if(command.equals(LED_STRIP_OFF))
            turnOff();
        else if(command.equals(LED_STRIP_GREEN))
            turnGreen();
        else if(command.equals(LED_STRIP_CYAN))
            turnCyan();
        else if(command.equals(LED_STRIP_MAGENTA))
            turnMagenta();

        return "";
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
            LOGGER.log(Level.WARNING, log_led_strip_timeout);
        }
        catch (IOException e) {
            e.printStackTrace(); // TODO : catch errors.
        }
        finally {
            try {
                if(writer != null)
                    writer.close();

                if(socket != null)
                    socket.close();
            }
            catch (IOException e) {
                e.printStackTrace(); // TODO : catch errors.
            }
        }
    }

    public void reverseLights() {
        if(isOn) {
            turnOff();
            isOn = false;
            // TODO : warn the client.
        }
        else {
            turnOn();
            isOn = true;
            // TODO : warn the client.
        }
    }

    public void turnOn() {
        byte[] code_on = new byte[] {(byte)0x71, (byte)0x23, (byte)0x0f, (byte)0xa3};
        sendByteCode(code_on);
        LOGGER.log(Level.FINE, log_led_strip_on);
        // TODO : warn the client.
    }

    public void turnOff() {
        byte[] code_off = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_off);
        LOGGER.log(Level.FINE, log_led_strip_off);
        // TODO : warn the client.
    }

    public void turnGreen() {
        byte[] code_green = new byte[] {(byte)0x31, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:ff:00:00:00:0f:3e
        sendByteCode(code_green);
        LOGGER.log(Level.FINE, log_led_strip_green);
        // TODO : warn the client.
    }

    public void turnCyan() {
        byte[] code_cyan = new byte[] {(byte)0x31, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:00:ff:ff:00:00:0f:3e
        sendByteCode(code_cyan);
        LOGGER.log(Level.FINE, log_led_strip_cyan);
        // TODO : warn the client.
    }

    public void turnMagenta() {
        byte[] code_magenta = new byte[] {(byte)0x31, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:00:ff:00:00:0f:3e
        sendByteCode(code_magenta);
        LOGGER.log(Level.FINE, log_led_strip_magenta);
        // TODO : warn the client.
    }

    public void turnWhite() {
        byte[] code_white = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_white);
        LOGGER.log(Level.FINE, log_led_strip_white);
        // TODO : warn the client.
    }
}
