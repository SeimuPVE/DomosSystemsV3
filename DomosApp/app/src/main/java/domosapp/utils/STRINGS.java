package domosapp.utils;


public class STRINGS {
    // Database.
    public static final String DATABASE_NAME = "domos.db";
    public static final int DATABASE_VERSION = 1;
    public static final String MODULE_DATABASE_CREATE = "create table MODULE(ID integer primary key autoincrement, TYPE text, NAME text, LABEL text, COMMAND text);";
    public static final String USER_DATABASE_CREATE = "create table USER(ID integer primary key autoincrement, PSEUDO text, PASSWORD text);";

    // Module table.
    public static final String MODULE_TABLE_NAME = "MODULE";
    public static final String MODULE_TYPE = "TYPE";
    public static final String MODULE_NAME = "NAME";
    public static final String MODULE_LABEL = "LABEL";
    public static final String MODULE_COMMAND = "COMMAND";
    public static final String MODULE_ADD_SUCCESS = "Module Info Saved";

    // User table.
    public static final String USER_TABLE_NAME = "USER";
    public static final String USER_PSEUDO = "PSEUDO";
    public static final String USER_PASSWORD = "PASSWORD";
    public static final String USER_ADD_SUCCESS = "User Info Saved";

    // Add module dialog.
    public static final String ADD_MODULE = "Add Module";
    public static final String CANCEL = "Cancel";
    public static final String ADD = "Add";
    public static final String ADD_MODULE_DIALOG = "Add module dialog.";
}
