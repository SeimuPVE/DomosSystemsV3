package modules;

import msc.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lights extends ModulePattern {
    private static List<String> commandList = new ArrayList<String>();
    private static List<String> codesOn = new ArrayList<String>();
    private static List<String> codesOff = new ArrayList<String>();
    private static List<Boolean> switchStates = new ArrayList<Boolean>();

    // TODO : code it in a better way (with a configuration file for example).
    public Lights() throws Exception {
        commandList.add("L_BACK");
        commandList.add("L_FRONT");
        commandList.add("L_BED");
        commandList.add("TV");

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

        for(i = 0; i < commandList.size(); i++) {
            if(command.equals(commandList.get(i) + "_ON"))
                switchOn(i);
            else if(command.equals(commandList.get(i) + "_OFF"))
                switchOff(i);
            else if(command.equals(commandList.get(i) + "_REVERSE"))
                switchReverse(i);
        }

        if(command.equals("DESKTOP_REVERSE")) {
            switchReverse(0); // exec("L_BACK_REVERSE");
            switchReverse(1); // exec("L_FRONT_REVERSE");
        }

        return "";
    }

    private void switchOn(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOn.get(index) + "\" > /dev/ttyUSB0"}).waitFor();
            switchStates.set(index, true);
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void switchOff(int index) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "echo \"" + codesOff.get(index) + "\" > /dev/ttyUSB0"}).waitFor();
            switchStates.set(index, false);
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void switchReverse(int index) {
        if(switchStates.get(index))
            switchOff(index);
        else
            switchOn(index);
    }
}
