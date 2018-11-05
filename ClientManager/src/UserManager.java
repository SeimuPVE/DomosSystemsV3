import java.io.*;
import java.util.Scanner;


public class UserManager {
    UserList users;
    Scanner scanner = new Scanner(System.in);

    private static String filepath = STRINGS.userfile;

    public UserManager() {
        File file = new File(filepath);

        // Load users.
        if(file.exists() && !file.isDirectory())
            load();
        else
            users = new UserList();

        // If there is not any admin, add it and load again.
        if(!users.adminExists())
            adminAdder();
    }

    public String askPassword() {
        String password = null, password_confirmer = null;

        while(password == null || !password.equals(password_confirmer)) {
            if(password != null) // Previous password was wrong.
                System.out.println(STRINGS.password_error_confirmation);

            // Ask for the password.
            System.out.println(STRINGS.ask_password);
            Console console = System.console();

            if(console == null) {
                // Can't hide hide the password because of the debug mode.
                System.out.println(STRINGS.debug_password);
                password = scanner.nextLine();
            }
            else
                password = new String(console.readPassword());

            // Check the password.
            System.out.println(STRINGS.ask_password_confirmer);
            password_confirmer = scanner.nextLine();
        }

        return password;
    }

    public void adminAdder() {
        // Create the admin and write it on the users file.
        users.add(STRINGS.admin_name, askPassword());
        save();
    }

    public void userAdder() {
        String username, password;

        // Ask for the user name and the password.
        System.out.println(STRINGS.ask_username_to_add);
        username = scanner.nextLine();

        password = askPassword();

        users.add(username, password);
        save();
    }

    public void passwordChanger() {
        User user;
        String username = null, password = null;
        int tries = 0;

        // Ask for the user.
        while(username == null) {
            System.out.println(STRINGS.ask_username);
            username = scanner.nextLine();
        }

        user = users.get(username);

        if(user != null) {
            // Ask for the current password.
            while(!User.hashPassword(password).equals(user.getHashedPassword()) && tries < 3) {
                System.out.println(STRINGS.enter_current_password);
                password = scanner.nextLine();

                if(!User.hashPassword(password).equals(user.getHashedPassword())) {
                    System.out.println(STRINGS.wrong_password);
                    tries++;
                }
            }

            if(tries == 3)
                System.out.println(STRINGS.fail_password);
            else {
                // Change the password.
                user.hashAndSetPassword(askPassword());
                save();
            }
        }
        else
            System.out.println(STRINGS.user_error);
    }

    public void userDeleter() {
        User user, admin;
        String username = null, admin_password = null;
        int tries = 0;

        // Ask for the user.
        while(username == null) {
            System.out.println(STRINGS.ask_username);
            username = scanner.nextLine();
        }

        if(!username.equals(STRINGS.admin_name)) {
            user = users.get(username);
            admin = users.get(STRINGS.admin_name);

            if(user != null) {
                // Ask for the admin password.
                while(!User.hashPassword(admin_password).equals(admin.getHashedPassword()) && tries < 3) {
                    System.out.println(STRINGS.enter_admin_password);
                    admin_password = scanner.nextLine();

                    if(!User.hashPassword(admin_password).equals(admin.getHashedPassword())) {
                        System.out.println(STRINGS.wrong_password);
                        tries++;
                    }
                }

                if(tries == 3)
                    System.out.println(STRINGS.fail_password);
                else {
                    // Delete the user.
                    users.delete(username);
                    save();
                }

                save();
            }
        }
        else
            System.out.println(STRINGS.dont_delete_admin);
    }

    public void printUsers() {
        users.printUsers();
    }

    public void load() {
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
    }

    public void save() {
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
