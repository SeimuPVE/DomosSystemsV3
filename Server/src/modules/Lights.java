package modules;

import msc.ConfigReader;
import rsc.CODES;
import rsc.STRINGS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// TODO : extract strings and change it to be more flexible.
public class Lights extends ModulePattern {
    private static List<String> commandsOn = new ArrayList<>();
    private static List<String> commandsOff = new ArrayList<>();
    private static List<String> commandsReverse = new ArrayList<>();
    private static List<String> codesOn = new ArrayList<>();
    private static List<String> codesOff = new ArrayList<>();
    private static List<Boolean> switchStates = new ArrayList<>();

    public Lights() {
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
        commandsReverse.add(CODES.DESKTOP_REVERSE);

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

    public String exec(String command) {
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
            // Execute command.
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOn.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();

            // Switch state of the light.
            switchStates.set(index, true);

            // Log it to the client.
            logSucces(STRINGS.log_light_turned_on);
        }
        catch (InterruptedException | IOException e) {
            logError(e.getMessage());
        }
    }

    private void switchOff(int index) {
        try {
            // Execute command.
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOff.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();

            // Switch state of the light.
            switchStates.set(index, false);

            // Log it to the client.
            logSucces(STRINGS.log_light_turned_off);
        }
        catch (InterruptedException | IOException e) {
            logError(e.getMessage());
        }
    }

    private void switchReverse(int index) {
        if(switchStates.get(index))
            switchOff(index);
        else
            switchOn(index);
    }
}
