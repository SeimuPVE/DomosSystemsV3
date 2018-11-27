package msc;

import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ConfigReader {
    private static final String configFilePath = "config.cfg";

    public static String readValue(String key) {
        try {
            String value = "";

            // Open the configFile.
            BufferedReader fileReader = new BufferedReader(new FileReader(configFilePath));

            // While there isn't the key, we read the next line (ignore if it's a comment).
            while((value != null && !value.contains(key)) || value.startsWith(STRINGS.comment_char))
                value = fileReader.readLine();

            if(value == null) {
                if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                    Logger.log(Logger.LevelSEVERE, ConfigReader.class.getName(), STRINGS.log_config_reader_not_key);

                return STRINGS.log_config_reader_not_key;
            }

            // We split the correct line to keep only the value, we also delete the ';'.
            value = value.split(" = ")[1];
            value = value.split(";")[0];

            return value;
        }
        catch (IOException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                Logger.log(Logger.LevelSEVERE, ConfigReader.class.getName(), e.getMessage());
        }

        return "";
    }
}
