package domosapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.domosapp.R;

import domosapp.adapters.UserDatabaseAdapter;
import domosapp.models.User;


public class LoginActivity extends AppCompatActivity {
    private UserDatabaseAdapter userDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        userDatabaseAdapter = new UserDatabaseAdapter(getBaseContext());

        if(UserDatabaseAdapter.getUser() == null)
            createLoginView();
        else
            launchMainActivity();
    }

    private void createLoginView() {
        Button btn_login;

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et_username;
                EditText et_password;

                et_username = findViewById(R.id.et_username);
                et_password = findViewById(R.id.et_password);

                String username = et_username.getText().toString().trim();
                String password = User.hashPassword(et_password.getText().toString().trim()); // We work with only hashed passwords.

                userDatabaseAdapter.insertUser(username, password);

                launchMainActivity();
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
