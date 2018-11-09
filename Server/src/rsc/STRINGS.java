package rsc;


public class STRINGS {
    // Server.
    public static final String log_server_up = "Server up !";
    public static final String log_server_closed = "Server closed !";

    // Client.
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
    public static final String log_environment_error = "Error.";

    // ConfigReader.
    public static final String log_config_reader_not_key = "Key not found in config file, the concerned key is : ";
    public static final String comment_char = "//";

    public static final String led_strip_ip = "led_strip_ip";
    public static final String led_strip_port = "led_strip_port";
    public static final String led_strip_timeout = "led_strip_timeout";
    public static final String environment_sensor_ip = "environment_sensor_ip";
    public static final String environment_sensor_port = "environment_sensor_port";
    public static final String environment_sensor_timeout = "environment_sensor_timeout";
    public static final String sensors_automater_timeout = "sensors_automater_timeout";
}
