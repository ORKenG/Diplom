package youtube.demo.youtubedemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.youtubedemo.AsyncTasks.LoadBlacklist;
import youtube.demo.youtubedemo.MyAdapter3;
import youtube.demo.youtubedemo.R;

/**
 * Created by Cypher on 10.03.2017.
 */

public class MyUser_Profile extends AppCompatActivity {
    TextView job_count;
    TextView name_user;
    TextView surname_user;
    ListView blacklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

       /* job_count = (TextView) findViewById(R.id.job_count);
        name_user = (TextView) findViewById(R.id.name_user);
        surname_user = (TextView) findViewById(R.id.surname_user);
        Intent intent = getIntent();
        String txt = "Number of jobs added: " + intent.getStringExtra("count");
        job_count.setText(txt);
        name_user.setText(intent.getStringExtra("name"));
        surname_user.setText(intent.getStringExtra("surname"));*/
        blacklist = (ListView) findViewById(R.id.blacklist);
        LoadBlacklist loadBlacklist = new LoadBlacklist();
        loadBlacklist.execute();
        try {
            ArrayList<ArrayList<String>> data = loadBlacklist.get();
            MyAdapter3 myAdapter3 = new MyAdapter3(this, data);
            blacklist.setAdapter(myAdapter3);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
