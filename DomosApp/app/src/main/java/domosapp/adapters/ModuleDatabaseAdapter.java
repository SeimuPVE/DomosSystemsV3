package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import domosapp.models.Module;
import domosapp.utils.DataBaseHelper;
import domosapp.utils.STRINGS;

import java.util.ArrayList;
import java.util.List;


public class ModuleDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public ModuleDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, STRINGS.DATABASE_NAME, null, STRINGS.DATABASE_VERSION);
    }

    // Method to insert a record in Table.
    public void insertEntry(String type, String name, String label, String command) {
        try {
            ContentValues newValues = new ContentValues();

            // Assign values for each column.
            newValues.put(STRINGS.MODULE_TYPE, type);
            newValues.put(STRINGS.MODULE_NAME, name);
            newValues.put(STRINGS.MODULE_LABEL, label);
            newValues.put(STRINGS.MODULE_COMMAND, command);

            // Insert the row into your table.
            db = dbHelper.getWritableDatabase();

            db.insert(STRINGS.MODULE_TABLE_NAME, null, newValues);
            Toast.makeText(context, STRINGS.MODULE_ADD_SUCCESS, Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    // Method to get the password  of userName.
    public List<Module> getAllModules() {
        List<Module> moduleList = new ArrayList<>();

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(STRINGS.MODULE_TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex(STRINGS.MODULE_TYPE));
            String name = cursor.getString(cursor.getColumnIndex(STRINGS.MODULE_NAME));
            String label = cursor.getString(cursor.getColumnIndex(STRINGS.MODULE_LABEL));
            String command = cursor.getString(cursor.getColumnIndex(STRINGS.MODULE_COMMAND));

            moduleList.add(new Module(type, name, label, command));
        }

        cursor.close();

        return moduleList;
    }
}