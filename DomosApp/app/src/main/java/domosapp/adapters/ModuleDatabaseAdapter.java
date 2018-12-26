package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.domosapp.R;

import domosapp.models.Module;
import domosapp.utils.DataBaseHelper;
import domosapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ModuleDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public ModuleDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Method to insert a record in Table.
    public void insertEntry(String type, String name, String label, String command) {
        try {
            ContentValues newValues = new ContentValues();

            // Assign values for each column.
            newValues.put(Constants.MODULE_TYPE, type);
            newValues.put(Constants.MODULE_NAME, name);
            newValues.put(Constants.MODULE_LABEL, label);
            newValues.put(Constants.MODULE_COMMAND, command);

            // Insert the row into your table.
            db = dbHelper.getWritableDatabase();

            db.insert(Constants.MODULE_TABLE_NAME, null, newValues);
            db.close();
            Toast.makeText(context, context.getString(R.string.module_added), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void deleteEntry(int id) {
        db = dbHelper.getWritableDatabase();
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.delete(Constants.MODULE_TABLE_NAME, Constants.MODULE_ID + "=?", whereArgs);
        db.close();
        Toast.makeText(context, context.getString(R.string.module_deleted), Toast.LENGTH_LONG).show();
    }

    public void updateEntry(int id, String name, String label, String command) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.MODULE_NAME, name);
        cv.put(Constants.MODULE_LABEL, label);
        cv.put(Constants.MODULE_COMMAND, command);
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.update(Constants.MODULE_TABLE_NAME, cv, Constants.MODULE_ID + "=?", whereArgs);
        db.close();
        Toast.makeText(context, context.getString(R.string.module_updated), Toast.LENGTH_LONG).show();
    }

    // Method to get the password  of userName.
    public List<Module> getAllModules() {
        List<Module> moduleList = new ArrayList<>();

        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(Constants.MODULE_TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants.MODULE_ID));
            String type = cursor.getString(cursor.getColumnIndex(Constants.MODULE_TYPE));
            String name = cursor.getString(cursor.getColumnIndex(Constants.MODULE_NAME));
            String label = cursor.getString(cursor.getColumnIndex(Constants.MODULE_LABEL));
            String command = cursor.getString(cursor.getColumnIndex(Constants.MODULE_COMMAND));
            Module module = new Module(type, name, label, command);
            module.setId(id);
            moduleList.add(module);
        }

        cursor.close();

        return moduleList;
    }
}
