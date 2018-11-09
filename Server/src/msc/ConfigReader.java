package msc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rsc.STRINGS.comment_char;
import static rsc.STRINGS.log_config_reader_not_key;


public class ConfigReader {
    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());

    private static final String configFilePath = "config.cfg";

    public static String readValue(String key) {
        try {
            String value = "";

            // Open the configFile.
            BufferedReader fileReader = new BufferedReader(new FileReader(configFilePath));

            // While there isn't the key, we read the next line (ignore if it's a comment).
            while((value != null && !value.contains(key)) || value.startsWith(comment_char))
                value = fileReader.readLine();

            if(value == null) {
                LOGGER.log(Level.SEVERE, log_config_reader_not_key);
                return log_config_reader_not_key;
            }

            // We split the correct line to keep only the value, we also delete the ';'.
            value = value.split(" = ")[1];
            value = value.split(";")[0];

            return value;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace(); // TODO : catch errors.
        }
        catch (IOException e) {
            e.printStackTrace(); // TODO : catch errors.
        }

        return "";
    }
}
