package modules;

import msc.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Lights extends ModulePattern {
    private static List<String> commandsOn = new ArrayList<String>();
    private static List<String> commandsOff = new ArrayList<String>();
    private static List<String> commandsReverse = new ArrayList<String>();
    private static List<String> codesOn = new ArrayList<String>();
    private static List<String> codesOff = new ArrayList<String>();
    private static List<Boolean> switchStates = new ArrayList<Boolean>();

    public Lights() throws Exception {
        commandsOn.add(CODES.L_BACK_ON);
        commandsOn.add(CODES.L_FRONT_ON);
        commandsOn.add(CODES.L_BED_ON);
        commandsOn.add(CODES.TV_ON);
        commandsOff.add(CODES.L_BACK_OFF);
        commandsOff.add(CODES.L_FRONT_OFF);
        commandsOff.add(CODES.L_BED_OFF);
        commandsOff.add(CODES.TV_OFF);
        commandsReverse.add(CODES.L_BACK_REVERSE);
        commandsReverse.add(CODES.L_FRONT_REVERSE);
        commandsReverse.add(CODES.L_BED_REVERSE);
        commandsReverse.add(CODES.TV_REVERSE);
        commandsReverse.add(CODES.DESKTOP_REVERSE); // TODO : Change it in client to be more flexible.

        codesOn.add(ConfigReader.readValue("code_on_1"));
        codesOn.add(ConfigReader.readValue("code_on_2"));
        codesOn.add(ConfigReader.readValue("code_on_3"));
        codesOn.add(ConfigReader.readValue("code_on_4"));

        codesOff.add(ConfigReader.readValue("code_off_1"));
        codesOff.add(ConfigReader.readValue("code_off_2"));
        codesOff.add(ConfigReader.readValue("code_off_3"));
        codesOff.add(ConfigReader.readValue("code_off_4"));

        for(int i = 0; i < 4; i++)
            switchStates.add(false);
    }

    public String exec(String command) { // TODO : manage it in a better way.
        int i;

        if(command.equals(CODES.DESKTOP_REVERSE)) {
            switchReverse(0);
            switchReverse(1);
        }
        else if(commandsOn.contains(command))
            switchOn(commandsOn.indexOf(command));
        else if(commandsOff.contains(command))
            switchOff(commandsOff.indexOf(command));
        else if(commandsReverse.contains(command))
            switchOn(commandsReverse.indexOf(command));

        return "";
    }

    private void switchOn(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOn.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();
            switchStates.set(index, true);
            // TODO : send message to warn that the light turned ON.
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace(); // TODO : catch errors.
        }
    }

    private void switchOff(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOff.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();
            switchStates.set(index, false);
            // TODO : send message to warn that the light turned OFF.
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace(); // TODO : catch errors.
        }
    }

    private void switchReverse(int index) {
        if(switchStates.get(index))
            switchOff(index);
        else
            switchOn(index);
    }
}
