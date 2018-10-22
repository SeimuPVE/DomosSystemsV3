import java.util.Scanner;


public class Main {
    public static void main(String[] argv) {
        // Call the menu.
        runMenu();
    }

    public static void runMenu() {
        // Load file and print users.
        ClientManager clientManager = new ClientManager();
        clientManager.printUsers();

        Scanner scanner = new Scanner(System.in);

        // Menu for actions.
        int choice = askChoice(scanner, clientManager);

        while(choice != 0) {
            while(choice < 0 || choice > 3) {
                System.out.println(STRINGS.menu_wrong_choice);
                System.out.print(STRINGS.menu_choice_asker);
                choice = scanner.nextInt();
            }

            if(choice == 1)
                clientManager.userAdder();
            else if(choice == 2)
                clientManager.passwordChanger();
            else if(choice == 3)
                clientManager.userDeleter();

            // Ask choice.
            System.out.println(STRINGS.line_jumper);
            choice = askChoice(scanner, clientManager);
        }
    }

    public static int askChoice(Scanner scanner, ClientManager clientManager) {
        // Ask choice.
        System.out.println(STRINGS.menu_present_users);
        clientManager.printUsers();

        System.out.println(STRINGS.menu_main_line_1);
        System.out.println(STRINGS.menu_main_line_2);

        System.out.print(STRINGS.menu_choice_asker);
        return scanner.nextInt();
    }
}
