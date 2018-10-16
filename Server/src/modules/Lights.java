package modules;

import msc.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static modules.CODES.*;


public class Lights extends ModulePattern {
    private static List<String> commandList = new ArrayList<String>();
    private static List<String> codesOn = new ArrayList<String>();
    private static List<String> codesOff = new ArrayList<String>();
    private static List<Boolean> switchStates = new ArrayList<Boolean>();

    public Lights() throws Exception {
        commandList.add(L_BACK_ON);
        commandList.add(L_FRONT_ON);
        commandList.add(L_BED_ON);
        commandList.add(TV_ON);
        commandList.add(L_BACK_OFF);
        commandList.add(L_FRONT_OFF);
        commandList.add(L_BED_OFF);
        commandList.add(TV_OFF);
        commandList.add(L_BACK_REVERSE);
        commandList.add(L_FRONT_REVERSE);
        commandList.add(L_BED_REVERSE);
        commandList.add(TV_REVERSE);
        commandList.add(DESKTOP_REVERSE);

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

        if(command.equals(DESKTOP_REVERSE)) {
            switchReverse(0);
            switchReverse(1);
        }

        for(i = 0; i < commandList.size(); i++) {
            if(command.equals(commandList.get(i)) && command.contains(ON))
                switchOn(i);
            else if(command.equals(commandList.get(i)) && command.contains(OFF))
                switchOff(i - commandList.indexOf(L_BACK_OFF));
            else if(command.equals(commandList.get(i)) && command.contains(REVERSE))
                switchReverse(i - commandList.indexOf(L_BACK_REVERSE));
        }

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
