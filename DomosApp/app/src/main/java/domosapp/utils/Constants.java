package domosapp.utils;


public class Constants {
    public static final String api_tag = "SEIMU_APP";

    // Hash salt.
    public static final String SALT = "MySaltNigga"; // TODO : put it in a config file and be careful with " char.

    // Database.
    public static final String DATABASE_NAME = "domos.db";
    public static final int DATABASE_VERSION = 1;
    public static final String MODULE_DATABASE_CREATE = "create table MODULE(ID integer primary key autoincrement, TYPE text, NAME text, LABEL text, COMMAND text);";
    public static final String USER_DATABASE_CREATE = "create table USER(ID integer primary key autoincrement, PSEUDO text, PASSWORD text);";

    // Module table.
    public static final String MODULE_TABLE_NAME = "MODULE";
    public static final String MODULE_ID = "ID";
    public static final String MODULE_TYPE = "TYPE";
    public static final String MODULE_NAME = "NAME";
    public static final String MODULE_LABEL = "LABEL";
    public static final String MODULE_COMMAND = "COMMAND";

    // User table.
    public static final String USER_TABLE_NAME = "USER";
    public static final String USER_PSEUDO = "PSEUDO";
    public static final String USER_PASSWORD = "PASSWORD";
    public static final String USER_ADD_SUCCESS = "User Info Saved";

    // Add module dialog.
    public static final String ADD_MODULE = "Add Module";
    public static final String CANCEL = "Cancel";
    public static final String ADD = "Add";
    public static final String UPDATE = "Update";
    public static final String ADD_MODULE_DIALOG = "Add module dialog.";
    public static final String EDIT_MODULE_DIALOG = "Edit module dialog.";

    // Action module dialog
    public static final String ACTION_MODULE_TITLE="Module actions";
    public static final String BUNDLE_MODULE_KEY = "moduleKey";

    // Networking.
    public static final String null_user = "null:null:";
    public static final String code_separator = ":";
}
