package clientManager;

import msc.ConfigReader;
import msc.Logger;
import rsc.CONF_CODES;
import rsc.STRINGS;

import java.io.*;


public class UserSaverLoader {
    private static String filepath = ConfigReader.readValue(CONF_CODES.users_filepath);

    public static String getFilepath() {
        return filepath;
    }

    public static UserList load() {
        UserList users = new UserList();
        FileInputStream fileIn = null;
        ObjectInputStream in = null;

        try {
            fileIn = new FileInputStream(filepath);
            in = new ObjectInputStream(fileIn);

            users = (UserList) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
        }
        finally {
            if(in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                        Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
                }
            }

            if(fileIn != null) {
                try {
                    fileIn.close();
                }
                catch (IOException e) {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                        Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
                }
            }
        }

        return users;
    }

    public static void save(UserList users) {
        File previous_save = new File(filepath + STRINGS.save_extension);
        File new_save = new File(filepath);
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        // Delete last save.
        if(previous_save.exists() && !previous_save.isDirectory())
            previous_save.delete();

        // Save the current file.
        if(new_save.exists() && !new_save.isDirectory())
            new_save.renameTo(previous_save);

        try {
            fileOut = new FileOutputStream(filepath);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
        }
        catch (IOException e) {
            if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
        }
        finally {
            if(out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                        Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
                }
            }

            if(fileOut != null) {
                try {
                    fileOut.close();
                }
                catch (IOException e) {
                    if(Integer.parseInt(ConfigReader.readValue(CONF_CODES.verbose_level)) >= 0)
                        Logger.log(Logger.LevelSEVERE, UserSaverLoader.class.getName(), e.getMessage());
                }
            }
        }
    }
}
