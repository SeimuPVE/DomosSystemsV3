package automaters;

import modules.EnvironmentSensors;
import msc.ConfigReader;
import rsc.STRINGS;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class SensorsAutomater implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SensorsAutomater.class.getName());

    private EnvironmentSensors environmentSensors;
    private int timeout;

    public SensorsAutomater(EnvironmentSensors environmentSensors) {
        timeout = Integer.parseInt(ConfigReader.readValue(STRINGS.sensors_automater_timeout));

        this.environmentSensors = environmentSensors;
    }

    @Override
    public void run() {
        while(true) {
            try {
                environmentSensors.updateSensors();
                sleep(4 * 60 * 1000);
            }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
