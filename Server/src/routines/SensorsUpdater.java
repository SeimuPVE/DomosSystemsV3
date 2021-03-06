package routines;

import modules.EnvironmentSensors;
import msc.ConfigReader;
import msc.Logger;
import rsc.CONF_CODES;

import static java.lang.Thread.sleep;


public class SensorsUpdater implements Runnable {
    private EnvironmentSensors environmentSensors;
    private int timeout;

    public SensorsUpdater(EnvironmentSensors environmentSensors) {
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
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
            }
        }
    }
}
