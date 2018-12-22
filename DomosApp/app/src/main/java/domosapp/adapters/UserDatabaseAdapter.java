package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    // Method to insert a record in Table.
    public void insertUser(String pseudo, String password) {
        db = dbHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();

        // Assign values for each column.
        newValues.put("PSEUDO", pseudo);
        newValues.put("PASSWORD", password);

        // Insert the row into your table.
        db.insert("USER", null, newValues);

        Toast.makeText(context, "User Info Saved", Toast.LENGTH_LONG).show();
    }

    // Method to get the password user.
    public static User getUser() {
        User user;
        String pseudo, password;
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("USER", null, null, null, null, null, null);

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToNext();
        pseudo = cursor.getString(cursor.getColumnIndex("PSEUDO"));
        password = cursor.getString(cursor.getColumnIndex("PASSWORD"));

        user = new User(pseudo, password);

        cursor.close();

        return user;
    }
}
