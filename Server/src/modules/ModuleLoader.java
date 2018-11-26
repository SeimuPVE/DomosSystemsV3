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
        Lights lights = new Lights();
        moduleList.add(lights);

        // TODO : move initialisations on a config file.
        moduleList.get(moduleList.indexOf(lights)).exec(CODES.LIGHT_OFF + "0");
        moduleList.get(moduleList.indexOf(lights)).exec(CODES.LIGHT_OFF + "1");

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
