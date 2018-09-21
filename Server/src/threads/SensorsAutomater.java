package threads;

import modules.EnvironmentSensors;

import static java.lang.Thread.sleep;

// TODO : add a thread to manage EnvironmentSensors.
public class SensorsAutomater implements Runnable {
    private EnvironmentSensors environmentSensors;

    public SensorsAutomater(EnvironmentSensors environmentSensors) {
        this.environmentSensors = environmentSensors;
    }

    @Override
    public void run() {
        while(true) {
            try {
                environmentSensors.updateSensors(); // TODO : correct the updater error (doesn't update).
                sleep(4 * 60 * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
