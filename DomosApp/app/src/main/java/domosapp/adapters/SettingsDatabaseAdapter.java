package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.domosapp.R;

import domosapp.models.Settings;
import domosapp.utils.Constants;
import domosapp.utils.DataBaseHelper;


public class SettingsDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public SettingsDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Method to insert a record in table.
    public void insertSettings(String ip, int port, String salt) {
        db = dbHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();

        // Assign values for each column.
        newValues.put(Constants.SETTINGS_IP, ip);
        newValues.put(Constants.SETTINGS_PORT, port);
        newValues.put(Constants.SETTINGS_SALT, salt);

        // Delete previous settings.
        deleteSettings();

        // Insert the row into your table.
        db.insert(Constants.SETTINGS_TABLE_NAME, null, newValues);

        Toast.makeText(context, context.getString(R.string.settings_saved), Toast.LENGTH_LONG).show();
    }

    public void deleteSettings() {
        db = dbHelper.getWritableDatabase();
        db.delete(Constants.SETTINGS_TABLE_NAME, null, null);
    }

    public static Settings getSettings() {
        Settings settings;
        String ip, salt;
        int port;

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(Constants.SETTINGS_TABLE_NAME, null, null, null, null, null, null);

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToNext();
        ip = cursor.getString(cursor.getColumnIndex(Constants.SETTINGS_IP));
        port = cursor.getInt(cursor.getColumnIndex(Constants.SETTINGS_PORT));
        salt = cursor.getString(cursor.getColumnIndex(Constants.SETTINGS_SALT));

        settings = new Settings(ip, port, salt);

        cursor.close();

        return settings;
    }
}
