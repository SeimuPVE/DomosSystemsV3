import java.io.*;
import java.util.ArrayList;


public class ClientManager {
    private static FileReader fileReader = null;
    private static BufferedReader bufferedReader = null;

    private ArrayList<User> users;

    public ClientManager() {
        try {
            // Open the file.
            fileReader = new FileReader(STRINGS.userfile);
            bufferedReader = new BufferedReader(fileReader);

            // Load users.
            loadUsers();

            // If there is not any admin, add it and load again.
            if(!adminExists()) {
                adminAdder();
                loadUsers();
            }
        }
        catch (FileNotFoundException e) {
            // If the file doesn't exists, create it and add an administrator.
            File file = new File(STRINGS.userfile);

            try {
                file.createNewFile();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }

            adminAdder();

            // Then load users.
            try {
                loadUsers();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bufferedReader != null)
                    bufferedReader.close();

                if(fileReader != null)
                    fileReader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadUsers() throws IOException {
        String current_line;

        while((current_line = bufferedReader.readLine()) != null)
            if(current_line.split(String.valueOf(STRINGS.separator)).length == 2)
                users.add(new User(current_line.split(String.valueOf(STRINGS.separator))[0], current_line.split(String.valueOf(STRINGS.separator))[1]));
    }

    public boolean adminExists() {
        if(users != null)
            for(User user : users)
                if(user.isAdmin())
                    return true;
        return false;
    }

    public void adminAdder() {
        System.out.println("adminAdder");
        // TODO Ask for the user name.

        // TODO Ask for the password.

        // TODO Check the password.

        // TODO Add the admin.

    }

    public void userAdder() {
        System.out.println("userAdder");
        // TODO Ask for a new user.

        // TODO Check information.

        // TODO Add user.

    }

    public void passwordChanger() {
        System.out.println("passwordChanger");
        // TODO Ask for the user.

        // TODO Ask the current password.

        // TODO Check the current password.

        // TODO Change the password.

    }

    public void userDeleter() {
        System.out.println("userDeleter");
        // TODO Ask for the user.

        // TODO Ask the current password (admin or user).

        // TODO Delete the user.

    }

    public void printUsers() {
        if(users != null)
            for(User user : users)
                System.out.println("- " + user.getLogin());
    }
}
