package scenarios;

import modules.Lights;
import msc.ConfigReader;
import msc.Logger;
import rsc.COMMANDS;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class ScenarioSunLight implements Runnable {
    private boolean state;
    private int start_time;
    private int end_time;
    private int current_hour;
    private int associated_light;


    public ScenarioSunLight() {
        state = false;
    }

    public void exec(String cmd) {
        int i;

        if(cmd.equals(COMMANDS.SCENARIO_SUN_LIGHT_OFF)) {
            state = false;
        }
        else if(cmd.startsWith(COMMANDS.SCENARIO_SUN_LIGHT)) { // SCENARIO_SUN_LIGHT_STARTTIME_ENDTIME_CMD.
            state = true;
            start_time = Integer.parseInt(cmd.split("_")[3]);
            end_time = Integer.parseInt(cmd.split("_")[4]);
            associated_light = Integer.parseInt(cmd.split("_")[5]);

            run();
        }
    }

    @Override
    public void run() {
        while(true) {
            current_hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            try {
                if(state && current_hour >= start_time && current_hour < end_time) {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 5)
                        msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_light_turned_on);

                    new Lights().switchOn(associated_light);
                    TimeUnit.MINUTES.sleep(30);
                }
                else {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 5)
                        msc.Logger.log(Logger.LevelFINE, this.getClass().getName(), STRINGS.log_light_turned_off);

                    new Lights().switchOff(associated_light);
                    TimeUnit.MINUTES.sleep(30);
                }
            }
            catch (InterruptedException e) {
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    msc.Logger.log(Logger.LevelSEVERE, this.getClass().getName(), e.getMessage());
            }
        }
    }
}
