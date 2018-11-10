package automaters;

import modules.EnvironmentSensors;
import msc.ConfigReader;
import rsc.CONF_CODES;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class SensorsAutomater implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SensorsAutomater.class.getName());

    private EnvironmentSensors environmentSensors;
    private int timeout;

    public SensorsAutomater(EnvironmentSensors environmentSensors) {
        timeout = Integer.parseInt(ConfigReader.readValue(CONF_CODES.sensors_automater_timeout));

        this.environmentSensors = environmentSensors;
    }

    @Override
    public void run() {
        while(true) {
            try {
                environmentSensors.updateSensors();
                sleep(Integer.parseInt((ConfigReader.readValue(CONF_CODES.sensors_automater_routine_time))));
            }
            catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
