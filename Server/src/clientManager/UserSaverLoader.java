package clientManager;

import java.io.*;

public class UserSaverLoader {
    private static String filepath = STRINGS.userfile; // TODO : move it into config file.

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
            e.printStackTrace();
        }
        finally {
            if(in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileIn != null) {
                try {
                    fileIn.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
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
            e.printStackTrace();
        }
        finally {
            if(out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOut != null) {
                try {
                    fileOut.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
