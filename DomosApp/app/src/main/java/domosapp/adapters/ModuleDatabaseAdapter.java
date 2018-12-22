package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import domosapp.models.Module;
import domosapp.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class ModuleDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public ModuleDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, "domos.db", null, 1); // TODO : change calls.
//        dbHelper = new DataBaseHelper(context, dbHelper.getDatabaseName(), null, dbHelper.getDatabaseVersion());
    }

    // Method to insert a record in Table.
    public void insertEntry(String type, String name, String label, String command) {
        try {
            ContentValues newValues = new ContentValues();

            // Assign values for each column.
            newValues.put("TYPE", type);
            newValues.put("NAME", name);
            newValues.put("LABEL", label);
            newValues.put("COMMAND", command);

            // Insert the row into your table.
            db = dbHelper.getWritableDatabase();

            db.insert("MODULE", null, newValues);
            Toast.makeText(context, "Module Info Saved", Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    // Method to get the password  of userName.
    public List<Module> getAllModules() {
        List<Module> moduleList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("MODULE", null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            String label = cursor.getString(cursor.getColumnIndex("LABEL"));
            String command = cursor.getString(cursor.getColumnIndex("COMMAND"));

            moduleList.add(new Module(type, name, label, command));
        }

        cursor.close();

        return moduleList;
    }
}
