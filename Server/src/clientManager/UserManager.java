package clientManager;

import rsc.STRINGS;

import java.io.*;
import java.util.Scanner;


public class UserManager {
    private UserList users;
    private Scanner scanner = new Scanner(System.in);

    public UserManager() {
        File file = new File(UserSaverLoader.getFilepath());

        // Load users.
        if(file.exists() && !file.isDirectory())
            users = UserSaverLoader.load();
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
        UserSaverLoader.save(users);
    }

    public void userAdder() {
        String username, password;

        // Ask for the user name and the password.
        System.out.println(STRINGS.ask_username_to_add);
        username = scanner.nextLine();

        if(!users.userAlreadyExists(username)) {

            password = askPassword();

            users.add(username, password);
            UserSaverLoader.save(users);
        }
        else
            System.out.println(STRINGS.error_user_already_exists);
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
                UserSaverLoader.save(users);
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
                    UserSaverLoader.save(users);
                }

                UserSaverLoader.save(users);
            }
        }
        else
            System.out.println(STRINGS.dont_delete_admin);
    }

    public void printUsers() {
        users.printUsers();
    }
}
