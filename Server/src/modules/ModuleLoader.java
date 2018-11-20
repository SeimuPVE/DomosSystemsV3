package modules;

import automaters.SensorsAutomater;
import msc.ConfigReader;
import msc.Logger;
import rsc.CODES;
import rsc.STRINGS;

import java.util.ArrayList;


public class ModuleLoader {
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

        Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_lights);
    }

    public static ArrayList<ModulePattern> loadEnvironmentSensors() {
        moduleList.add(new EnvironmentSensors());
        new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

        Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_environment_sensor);
        return moduleList;
    }

    public static ArrayList<ModulePattern> loadLedStrip() {
        moduleList.add(new LedStrip());

        Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_led_strip);
        return moduleList;
    }
}
