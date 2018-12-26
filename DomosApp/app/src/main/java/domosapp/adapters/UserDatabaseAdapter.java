package domosapp.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.domosapp.R;

import domosapp.models.User;
import domosapp.utils.DataBaseHelper;
import domosapp.utils.Constants;


public class UserDatabaseAdapter {
    private static SQLiteDatabase db;
    private static DataBaseHelper dbHelper;
    private final Context context;

    public UserDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    // Method to insert a record in Table.
    public void insertUser(String pseudo, String password) {
        db = dbHelper.getWritableDatabase();

        ContentValues newValues = new ContentValues();

        // Assign values for each column.
        newValues.put(Constants.USER_PSEUDO, pseudo);
        newValues.put(Constants.USER_PASSWORD, password);

        // Insert the row into your table.
        db.insert(Constants.USER_TABLE_NAME, null, newValues);

        Toast.makeText(context, context.getString(R.string.module_added), Toast.LENGTH_LONG).show();
    }

    public void deleteUser() {
        db = dbHelper.getWritableDatabase();

        db.delete(Constants.USER_TABLE_NAME, null, null);
    }

    // Method to get the password user.
    public static User getUser() {
        User user;
        String pseudo, password;
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(Constants.USER_TABLE_NAME, null, null, null, null, null, null);

        if (cursor.getCount() == 0)
            return null;

        cursor.moveToNext();
        pseudo = cursor.getString(cursor.getColumnIndex(Constants.USER_PSEUDO));
        password = cursor.getString(cursor.getColumnIndex(Constants.USER_PASSWORD));

        user = new User(pseudo, password);

        cursor.close();

        return user;
    }
}
