package com.ayoubidel.domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import com.ayoubidel.domosapp.models.Module;
import com.ayoubidel.domosapp.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ModuleDatabaseAdapter {

    static final String DATABASE_NAME = "database.db";
    static final int DATABASE_VERSION = 1;
    public static String getPassword = "";
    public static final int NAME_COLUMN = 1;
    public static final String DATABASE_CREATE = "create table MODULE( ID integer primary key autoincrement,TYPE text, IP  text,PORT  text,LABEL text,DESCRIPTION text); ";
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

    // method returns an Instance of the Database
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    // method to insert a record in Table
    public String insertEntry(String type, String ip, String port, String label, String description) {
        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("TYPE", type);
            newValues.put("IP", ip);
            newValues.put("PORT", port);
            newValues.put("LABEL", label);
            newValues.put("DESCRIPTION", description);
            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result = db.insert("MODULE", null, newValues);
            System.out.print(result);
            Toast.makeText(context, "Module Info Saved", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            System.out.println("Exceptions " + ex);
            Log.e("Note", "One row entered");
        }
        return "ok";
    }

    // method to get the password  of userName
    public List<Module> getAllModules() {
        List<Module> moduleList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("MODULE", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            String ip = cursor.getString(cursor.getColumnIndex("IP"));
            String port = cursor.getString(cursor.getColumnIndex("PORT"));
            String label = cursor.getString(cursor.getColumnIndex("LABEL"));
            String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
            moduleList.add(new Module(type, ip, port, label, description));
        }
        return moduleList;
    }
}
