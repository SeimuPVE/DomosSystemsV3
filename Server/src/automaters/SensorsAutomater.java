package automaters;

import modules.EnvironmentSensors;
import msc.ConfigReader;

import static java.lang.Thread.sleep;


public class SensorsAutomater implements Runnable {
    private EnvironmentSensors environmentSensors;

    private int timeout;

    public SensorsAutomater(EnvironmentSensors environmentSensors) {
        timeout = Integer.parseInt(ConfigReader.readValue("sensors_automater_timeout"));

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
                e.printStackTrace();
            }
        }
    }
}
