package modules;

import msc.ConfigReader;
import rsc.CODES;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Lights extends ModulePattern {
    private int numberOfLights = Integer.parseInt(ConfigReader.readValue(CONF_CODES.number_of_lights));
    private List<String> commandsOn = new ArrayList<>();
    private List<String> commandsOff = new ArrayList<>();
    private List<String> commandsReverse = new ArrayList<>();
    private List<String> codesOn = new ArrayList<>();
    private List<String> codesOff = new ArrayList<>();
    private List<Boolean> switchStates = new ArrayList<>();

    public Lights() {
        int i;

        for(i = 0; i < numberOfLights; i++) {
            codesOn.add(ConfigReader.readValue(CONF_CODES.code_on + "_" + String.valueOf(i)));
            codesOff.add(ConfigReader.readValue(CONF_CODES.code_off + "_" +  String.valueOf(i)));

            commandsOn.add(CODES.LIGHT_ON + String.valueOf(i));
            commandsOff.add(CODES.LIGHT_OFF  + String.valueOf(i));
            commandsReverse.add(CODES.LIGHT_REVERSE + String.valueOf(i));

            switchStates.add(false);
        }
    }

    public String exec(String command) {
        if(commandsOn.contains(command))
            switchOn(commandsOn.indexOf(command));
        else if(commandsOff.contains(command))
            switchOff(commandsOff.indexOf(command));
        else if(commandsReverse.contains(command))
            switchReverse(commandsReverse.indexOf(command));

        return STRINGS.log_error + STRINGS.unreconnized_command;
    }

    private void switchOn(int index) {
        try {
            // Execute command.
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOn.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();

            // Switch state of the light.
            switchStates.set(index, true);

            // Log it to the client.
            clientLogSuccess(STRINGS.log_light_turned_on);
        }
        catch (InterruptedException | IOException e) {
            clientLogError(e.getMessage());
        }
    }

    private void switchOff(int index) {
        try {
            // Execute command.
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOff.get(index) + "\" > " + ConfigReader.readValue("usb_port")}).waitFor();

            // Switch state of the light.
            switchStates.set(index, false);

            // Log it to the client.
            clientLogSuccess(STRINGS.log_light_turned_off);
        }
        catch (InterruptedException | IOException e) {
            clientLogError(e.getMessage());
        }
    }

    private void switchReverse(int index) {
        if(switchStates.get(index))
            switchOff(index);
        else
            switchOn(index);
    }
}
