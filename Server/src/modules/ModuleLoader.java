package modules;

import automaters.SensorsAutomater;
import msc.ConfigReader;
import msc.Logger;
import rsc.COMMANDS;
import rsc.CONF_CODES;
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
        if(ConfigReader.readValue("wifi_led_strip").equals("on"))
            loadWifiLedStrip();

        return moduleList;
    }

    public static ArrayList<ModulePattern> getModules() {
        return moduleList;
    }

    public static void loadLights() {
        Lights lights = new Lights();
        moduleList.add(lights);

        // TODO : move initialisations on a config file.
        moduleList.get(moduleList.indexOf(lights)).exec(COMMANDS.LIGHT_OFF + "0");
        moduleList.get(moduleList.indexOf(lights)).exec(COMMANDS.LIGHT_OFF + "1");

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
            Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_lights);
    }

    public static ArrayList<ModulePattern> loadEnvironmentSensors() {
        moduleList.add(new EnvironmentSensors());
        new Thread(new SensorsAutomater((EnvironmentSensors) moduleList.get(1))).start();

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
            Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_environment_sensor);

        return moduleList;
    }

    public static ArrayList<ModulePattern> loadLedStrip() {
        moduleList.add(new LedStrip());

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
            Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_led_strip);

        return moduleList;
    }

    public static ArrayList<ModulePattern> loadWifiLedStrip() {
        WIFILedStrip wifiLedStrip = new WIFILedStrip();
        moduleList.add(wifiLedStrip);

        wifiLedStrip.turnOff();

        if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 2)
            Logger.log(Logger.LevelFINE, ModuleLoader.class.getName(), STRINGS.loader_led_strip);

        return moduleList;
    }
}
