package rsc;


public class STRINGS {
    // General.
    public static final String yes = "yes";
    public static final String splitter_1 = ":";
    public static final String splitter_2 = ";";

    // Server.
    public static final String log_server_up = "Server up !";
    public static final String log_server_closed = "Server closed !";
    public static final String first_launch = "It is the first time that you launch the server.\nYou need to create a new user first, then just exit this program by choosing EXIT (choice 0) and the server will be launched.\n";

    // Client.
    public static final String unrecognized_command = "Unrecognized command.";
    public static final String log_new_client = "A new client is connected.";
    public static final String log_delete_client = "A client disconnected !";
    public static final String log_total_client = "Total : ";
    public static final String log_command = "Command : ";
    public static final String log_success = "SUCCESS : ";
    public static final String log_error = "ERROR : ";

    // ModulePattern.
    public static final String log_module_err = "Please, use a daughter class...";

    // Lights.
    public static final String log_light_turned_on = "Light turned on.";
    public static final String log_light_turned_off = "Light turned off.";

    // LedStrip.
    public static final String log_led_strip_timeout = "Timeout exception with led strip !";
    public static final String log_led_strip_on = "Lights on !";
    public static final String log_led_strip_off = "Lights off !";
    public static final String log_led_strip_green = "Lights green !";
    public static final String log_led_strip_cyan = "Led strip is cyan !";
    public static final String log_led_strip_magenta = "Led strip is magenta !";
    public static final String log_led_strip_white = "Led strip is white !";

    // EnvironmentSensors.
    public static final String log_environment_updated = "Environment sensor updated.";
    public static final String log_environment_timeout = "Timeout exception with environment sensors !";
    public static final String log_environment_no_route_to_host = "The environment sensor is not connected.";

    // ConfigReader.
    public static final String log_config_reader_not_key = "Key not found in config file, the concerned key is : ";
    public static final String comment_char = "//";

    // ClientManager.
    public static final String admin_name = "admin";
    public static final String menu_main_line_1 = "Please enter a choice :";
    public static final String menu_main_line_2 = "0=EXIT, 1=ADD, 2=CHANGE_PASSWORD, 3=DELETE";
    public static final String menu_present_users = "List of current users :";
    public static final String menu_choice_asker = "Choice : ";
    public static final String menu_wrong_choice = "Please use a valid choice.";
    public static final String ask_password = "Enter a new password :";
    public static final String ask_password_confirmer = "Confirm your password :";
    public static final String password_error_confirmation = "Passwords are not equals, please start again.";
    public static final String ask_username_to_add = "Please enter the name of the user to add :";
    public static final String ask_username = "Username :";
    public static final String save_extension = ".bak";
    public static final String enter_current_password = "Please enter the current password :";
    public static final String wrong_password = "Wrong password, try again.";
    public static final String fail_password = "Wrong password.";
    public static final String user_error = "The selected user doesn't exist.";
    public static final String enter_admin_password = "Please, enter the administrator password :";
    public static final String dont_delete_admin = "Your kidding me ? You really want to delete the administrator ? Ciao newbie...";
    public static final String debug_password = "DEBUG MODE : password not hidden in debug mode.";
    public static final String error_user_already_exists = "Error : the user already exists.";

    // Client login.
    public static final String connection_error_wrong_username = "Connection error : a user tried to authenticate but the username was wrong.";
    public static final String connection_error_wrong_password = "Connection error : a user tried to authenticate but the password was wrong.";
    public static final String code_not_valid = "Code not valid : ";
    public static final String connection_error = "Connection error, a problem occurred with your code message.";

    // Module loader.
    public static final String loader_lights = "Lights loaded.";
    public static final String loader_environment_sensor = "Environment sensors loaded.";
    public static final String loader_led_strip = "Led strip loaded.";
}
