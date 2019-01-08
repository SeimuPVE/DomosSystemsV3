package modules;

import msc.ConfigReader;
import msc.Logger;
import rsc.COMMANDS;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ChairPressure extends ModulePattern {
    private Socket socket = null;
    private BufferedReader reader = null;
    private PrintStream writer = null;

    private String ip;
    private int port;
    private int timeout;

    private boolean pressed;

    public ChairPressure() {
        ip = ConfigReader.readValue(CONF_CODES.chair_pressure_ip);
        port = Integer.parseInt(ConfigReader.readValue(CONF_CODES.chair_pressure_port));
        timeout = Integer.parseInt(ConfigReader.readValue(CONF_CODES.chair_pressure_timeout));

        observeModule();
    }

    public void observeModule() {
        String msg;

        // While it's true.
        while(true) {
            try {
                // Connect the module.
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), timeout);

                // Wait a message.
                msg = reader.readLine();

                // TODO : act in function of the message.
                if(msg.equals(COMMANDS.PRESSURE_PRESSED)) {
                    pressed = true;
                    setChanged();
                    notifyObservers(pressed);
                }
                else if(msg.equals(COMMANDS.PRESSURE_RELEASED)) {
                    pressed = false;
                    notifyObservers(pressed);
                }

                // Disconnect the module.
                socket.close();
            }
            catch (IOException e) {
                Logger.log(Logger.LevelSEVERE, this.getClass().getName(), STRINGS.log_char_pressure_error);
            }
        }
    }

    public boolean isPressed() {
        return pressed;
    }
}
