package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import domosapp.models.Module;
import domosapp.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class ModuleDatabaseAdapter {
    static final String DATABASE_NAME = "domos.db";
    static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "create table MODULE(ID integer primary key autoincrement, TYPE text, NAME text, LABEL text, COMMAND text);";
    public static SQLiteDatabase db;
    private final Context context;
    private static DataBaseHelper dbHelper;

    public ModuleDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ModuleDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    // Method returns an Instance of the Database.
    public SQLiteDatabase getDatabaseInstance() {
        return db;
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

            long result = db.insert("MODULE", null, newValues);
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

        while (cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            String label = cursor.getString(cursor.getColumnIndex("LABEL"));
            String command = cursor.getString(cursor.getColumnIndex("COMMAND"));

            moduleList.add(new Module(type, name, label, command));
        }

        return moduleList;
    }
}
