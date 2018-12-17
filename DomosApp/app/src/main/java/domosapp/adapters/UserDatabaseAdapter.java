package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import domosapp.models.User;
import domosapp.utils.DataBaseHelper;


public class UserDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public UserDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, "domos.db", null, 1); // TODO : change calls and do something more generic.
//        dbHelper = new DataBaseHelper(context, dbHelper.getDatabaseName(), null, dbHelper.getDatabaseVersion());
    }

    public UserDatabaseAdapter open() throws SQLException {
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
    public void insertUser(String pseudo, String password) {
        try {
            ContentValues newValues = new ContentValues();

            // Assign values for each column.
            newValues.put("PSEUDO", pseudo);
            newValues.put("PASSWORD", password);

            // Insert the row into your table.
            db = dbHelper.getWritableDatabase();

            long result = db.insert("MODULE", null, newValues);
            Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    // Method to get the password user.
    public static User getUser() {
        try {
            User user;
            String pseudo, password;
            db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query("USER", null, null, null, null, null, null);

            pseudo = cursor.getString(cursor.getColumnIndex("PSEUDO"));
            password = cursor.getString(cursor.getColumnIndex("PASSWORD"));

            user = new User(pseudo, password);

            return user;
        }
        catch(Exception e) {
            return null;
        }
    }
}
