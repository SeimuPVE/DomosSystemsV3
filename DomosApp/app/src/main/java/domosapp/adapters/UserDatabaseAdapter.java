package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import domosapp.models.User;
import domosapp.utils.DataBaseHelper;
import domosapp.utils.STRINGS;


public class UserDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public UserDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, STRINGS.DATABASE_NAME, null, STRINGS.DATABASE_VERSION);
    }

    // Method to insert a record in Table.
    public void insertUser(String pseudo, String password) {
        db = dbHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();

        // Assign values for each column.
        newValues.put(STRINGS.USER_PSEUDO, pseudo);
        newValues.put(STRINGS.USER_PASSWORD, password);

        // Insert the row into your table.
        db.insert(STRINGS.USER_TABLE_NAME, null, newValues);

        Toast.makeText(context, STRINGS.USER_ADD_SUCCESS, Toast.LENGTH_LONG).show();
    }

    // Method to get the password user.
    public static User getUser() {
        User user;
        String pseudo, password;
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(STRINGS.USER_TABLE_NAME, null, null, null, null, null, null);

        if(cursor.getCount() == 0)
            return null;

        cursor.moveToNext();
        pseudo = cursor.getString(cursor.getColumnIndex(STRINGS.USER_PSEUDO));
        password = cursor.getString(cursor.getColumnIndex(STRINGS.USER_PASSWORD));

        user = new User(pseudo, password);

        cursor.close();

        return user;
    }
}
