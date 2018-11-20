package modules;

import automaters.SensorsAutomater;
import msc.ConfigReader;
import rsc.CODES;
import rsc.STRINGS;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleLoader {
    private static final Logger LOGGER = Logger.getLogger(ModuleLoader.class.getName());

    private static ArrayList<ModulePattern> moduleList = new ArrayList<>();

    public static ArrayList<ModulePattern> loadModules() {
        if(ConfigReader.readValue("lights").equals("on"))
            loadLights();
        if(ConfigReader.readValue("environment_sensors").equals("on"))
            loadEnvironmentSensors();
        if(ConfigReader.readValue("led_strip").equals("on"))
            loadLedStrip();

        return moduleList;
    }

    public static ArrayList<ModulePattern> getModules() {
        return moduleList;
    }

    public static void loadLights() {
        moduleList.add(new Lights());

        // TODO : move initialisations on a config file.
        moduleList.get(0).exec(CODES.LIGHT_OFF + "_0");
        moduleList.get(0).exec(CODES.LIGHT_OFF + "_1");

        LOGGER.log(Level.FINE, STRINGS.loader_lights);
    }

    public static ArrayList<ModulePattern> loadEnvironmentSensors() {
        moduleList.add(new EnvironmentSensors());
        new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

        LOGGER.log(Level.FINE, STRINGS.loader_environment_sensor);
        return moduleList;
    }

    public static ArrayList<ModulePattern> loadLedStrip() {
        moduleList.add(new LedStrip());

        LOGGER.log(Level.FINE, STRINGS.loader_led_strip);
        return moduleList;
    }
}
