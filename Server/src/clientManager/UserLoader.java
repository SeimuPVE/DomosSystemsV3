package clientManager;

import rsc.STRINGS;

import java.io.File;

public class UserLoader {
    public static UserList loadUsers(String filepath) {
        UserList users;
        File file = new File(filepath);

        // Load users.
        if (file.exists() && !file.isDirectory())
            users = UserSaverLoader.load();
        else
            users = new UserList();

        // If there is not any admin, add it and load again.
        if (!users.adminExists()) {
            System.out.println(STRINGS.first_launch);
            ClientManager.run(null);

        }

        return users;
    }
}
