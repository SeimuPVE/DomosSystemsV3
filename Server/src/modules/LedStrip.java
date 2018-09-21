package modules;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

// TODO : set string into constants.
public class LedStrip extends ModulePattern {
    private Socket socket = null;
    private PrintStream writer = null;

    private final String ip = "192.168.0.111"; // TODO : add it on a configuration file.
    private final int port = 5577;
    private final int timeout = 5000;

    private boolean isOn = false;

    public String exec(String command) {
        if(command.equals("LED_STRIP_REVERSE"))
            reverseLights();
        else if(command.equals("LED_STRIP_ON"))
            turnOn();
        else if(command.equals("LED_STRIP_OFF"))
            turnOff();
        else if(command.equals("LED_STRIP_GREEN"))
            turnGreen();
        else if(command.equals("LED_STRIP_CYAN"))
            turnCyan();
        else if(command.equals("LED_STRIP_MAGENTA"))
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
            System.out.println("Timeout exception with led strip !");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(writer != null)
                    writer.close();

                if(socket != null)
                    socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
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
        System.out.println("Lights on !");
    }

    public void turnOff() {
        byte[] code_off = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_off);
        System.out.println("Lights off !");
    }

    public void turnGreen() {
        byte[] code_green = new byte[] {(byte)0x31, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:ff:00:00:00:0f:3e
        sendByteCode(code_green);
        System.out.println("Lights green !");
    }

    public void turnCyan() {
        byte[] code_cyan = new byte[] {(byte)0x31, (byte)0x00, (byte)0xff, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:00:ff:ff:00:00:0f:3e
        sendByteCode(code_cyan);
        System.out.println("Led strip is cyan !");
    }

    public void turnMagenta() {
        byte[] code_magenta = new byte[] {(byte)0x31, (byte)0xff, (byte)0x00, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x0f, (byte)0x3e}; // 31:ff:00:ff:00:00:0f:3e
        sendByteCode(code_magenta);
        System.out.println("Led strip is magenta !");
    }

    public void turnWhite() {
        byte[] code_white = new byte[] {(byte)0x71, (byte)0x24, (byte)0x0f, (byte)0xa4};
        sendByteCode(code_white);
        System.out.println("Led strip is white !");
    }
}
