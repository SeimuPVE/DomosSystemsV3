package clientManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class UserList implements Serializable {
    private ArrayList<User> users;
    private ArrayList<String> logins;


    public UserList() {
        users = new ArrayList<>();
        logins = new ArrayList<>();
    }

    public boolean adminExists() {
        if(users != null)
            for(User user : users)
                if(user.isAdmin())
                    return true;
        return false;
    }

    public boolean userAlreadyExists(String login) {
        if(users != null)
            for(User user : users)
                if(user.getLogin().equals(login))
                    return true;
        return false;
    }

    public User get(String username) {
        for(User user : users)
            if(user.getLogin().equals(username))
                return user;
        return null;
    }

    public void add(User user) {
        users.add(user);
        logins.add(user.getLogin());
    }

    public void add(String username, String password) {
        users.add(new User(username, password));
        logins.add(username);
    }

    public void changePassword(String username, String new_password) {
        for(User user : users)
            if(user.getLogin().equals(username))
                user.hashAndSetPassword(new_password);
    }

    public void delete(String username) {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()) {
            User user = iterator.next();

            if(user.getLogin().equals(username))
                iterator.remove();
        }
    }

    public void delete(User user) {
        delete(user.getLogin());
    }

    public void printUsers() {
        System.out.println(STRINGS.menu_present_users);

        if(users != null)
            for(User user : users)
                System.out.println("- " + user.getLogin());

        System.out.println();
    }
}
