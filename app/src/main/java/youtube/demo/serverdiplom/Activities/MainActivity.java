package youtube.demo.serverdiplom.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.LoadUserProfile;
import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.R;

//

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static boolean flagForMap = true;
    public static boolean flag = true;
    public static boolean flagForMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (flagForMap) {
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();
        } else {
            flagForMyProfile = true;
            Intent intent = new Intent(getBaseContext(), MyUser_Profile.class);
            LoadUserProfile l = new LoadUserProfile();
            l.execute();
            ArrayList<ArrayList<String>> counts = new ArrayList<>();
            counts.ensureCapacity(9);
            try {
                counts = l.get();
                ArrayList<ArrayList<String>> counts2 = new ArrayList<>();
                ArrayList<String> idd = new ArrayList<>();
                counts2.ensureCapacity(9);
                for (int i = 2; i < counts.size(); i++) {
                    ArrayList<String> line = new ArrayList<>();
                    line.add(0, counts.get(i).get(1));
                    line.add(1, counts.get(i).get(0));
                    line.add(2, counts.get(i).get(3));
                    line.add(3, counts.get(i).get(4));
                    counts2.add(i - 2, line);
                    idd.add(i - 2, counts.get(i).get(2));
                }
                intent.putExtra("review", counts2);
                intent.putExtra("id", idd);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            intent.putExtra("count", counts.get(0).get(0));
            intent.putExtra("name", counts.get(0).get(1));
            intent.putExtra("surname", counts.get(0).get(2));
            intent.putExtra("secondname", counts.get(0).get(3));
            intent.putExtra("phone", counts.get(0).get(4));
            intent.putExtra("mail", counts.get(0).get(5));
            intent.putExtra("avg", counts.get(1).get(0));
            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getBaseContext(), SettingActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            flagForMyProfile = true;
            Intent intent = new Intent(getBaseContext(), MyUser_Profile.class);
            LoadUserProfile l = new LoadUserProfile();
            l.execute();
            ArrayList<ArrayList<String>> counts = new ArrayList<>();
            counts.ensureCapacity(9);
            try {
                counts = l.get();
                ArrayList<ArrayList<String>> counts2 = new ArrayList<>();
                ArrayList<String> idd = new ArrayList<>();
                counts2.ensureCapacity(9);
                for (int i = 2; i < counts.size(); i++) {
                    ArrayList<String> line = new ArrayList<>();
                    line.add(0, counts.get(i).get(1));
                    line.add(1, counts.get(i).get(0));
                    line.add(2, counts.get(i).get(3));
                    line.add(3, counts.get(i).get(4));
                    counts2.add(i - 2, line);
                    idd.add(i - 2, counts.get(i).get(2));
                }
                intent.putExtra("review", counts2);
                intent.putExtra("id", idd);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            intent.putExtra("count", counts.get(0).get(0));
            intent.putExtra("name", counts.get(0).get(1));
            intent.putExtra("surname", counts.get(0).get(2));
            intent.putExtra("secondname", counts.get(0).get(3));
            intent.putExtra("phone", counts.get(0).get(4));
            intent.putExtra("mail", counts.get(0).get(5));
            intent.putExtra("avg", counts.get(1).get(0));
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();
        } else if (id == R.id.nav_slideshow) {

            SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();
            editor.remove("login");
            editor.remove("password");
            editor.apply();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            flag = !flag;

            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();


        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
