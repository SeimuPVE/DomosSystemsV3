package domosapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "domos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String MODULE_DATABASE_CREATE = "create table MODULE(ID integer primary key autoincrement, TYPE text, NAME text, LABEL text, COMMAND text);";
    private static final String USER_DATABASE_CREATE = "create table USER(ID integer primary key autoincrement, PSEUDO text, PASSWORD text);";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MODULE_DATABASE_CREATE);
        sqLiteDatabase.execSQL(USER_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }
}
