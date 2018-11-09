package clientManager;

import java.util.Scanner;

public class Menu {
    public static void runMenu() {
        // Load file and print users.
        UserManager userManager = new UserManager();
        Scanner scanner = new Scanner(System.in);

        // Menu for actions.
        int choice = askChoice(scanner, userManager);

        while(choice != 0) {
            while(choice < 0 || choice > 3) {
                System.out.println(STRINGS.menu_wrong_choice);
                System.out.print(STRINGS.menu_choice_asker);
                choice = scanner.nextInt();
            }

            if(choice == 1)
                userManager.userAdder();
            else if(choice == 2)
                userManager.passwordChanger();
            else if(choice == 3)
                userManager.userDeleter();

            // Ask choice.
            System.out.println();
            choice = askChoice(scanner, userManager);
        }
    }

    public static int askChoice(Scanner scanner, UserManager userManager) {
        // Ask choice.
        int result;

        userManager.printUsers();

        System.out.println(STRINGS.menu_main_line_1);
        System.out.println(STRINGS.menu_main_line_2);

        System.out.print(STRINGS.menu_choice_asker);
        result = scanner.nextInt();

        System.out.println();

        return result;
    }
}
