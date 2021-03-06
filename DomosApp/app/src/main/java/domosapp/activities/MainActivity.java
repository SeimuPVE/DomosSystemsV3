package domosapp.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.domosapp.R;

import domosapp.adapters.UserDatabaseAdapter;
import domosapp.fragments.AccountFragment;
import domosapp.fragments.HomeFragment;
import domosapp.fragments.LogoutFragment;
import domosapp.fragments.SettingsFragment;
import domosapp.models.User;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        User user = UserDatabaseAdapter.getUser();
        TextView header = navigationView.getHeaderView(0).findViewById(R.id.nav_header_pseudo);

        if (user != null)
            header.setText(user.getPseudo());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement.
        if (id == R.id.action_settings)
            return true;
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_account)
            fragmentManager.beginTransaction().replace(R.id.content_frame, new AccountFragment()).commit();
        else if (id == R.id.nav_home)
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        else if (id == R.id.nav_settings)
            fragmentManager.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
        else if (id == R.id.nav_logout)
            fragmentManager.beginTransaction().replace(R.id.content_frame, new LogoutFragment()).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public static void launchHomeActivity() {
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }
}
