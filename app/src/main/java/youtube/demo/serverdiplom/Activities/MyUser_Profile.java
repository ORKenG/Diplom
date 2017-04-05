package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.LoadBlacklist;
import youtube.demo.serverdiplom.MyAdapter2;
import youtube.demo.serverdiplom.MyAdapter3;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMap;

/**
 * Created by Cypher on 10.03.2017.
 */

public class MyUser_Profile extends AppCompatActivity {
    TextView job_count;
    TextView name_user;

    ListView blacklist;
    ListView view_review;
    Button show_review;
    Button show_blacklist;
    Button changeProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        flagForMap = true;
        changeProfile = (Button) findViewById(R.id.change_profile);

        show_blacklist = (Button) findViewById(R.id.show_blacklist);
        show_review = (Button) findViewById(R.id.show_review);
        show_blacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blacklist.setVisibility(View.VISIBLE);
                view_review.setVisibility(View.GONE);
            }
        });
        show_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blacklist.setVisibility(View.GONE);
                view_review.setVisibility(View.VISIBLE);
            }
        });
        job_count = (TextView) findViewById(R.id.job_count);
        name_user = (TextView) findViewById(R.id.name_user);

        view_review = (ListView) findViewById(R.id.view_review);
        final Intent intent = getIntent();
        String txt = "Number of jobs added: " + intent.getStringExtra("count");
        job_count.setText(txt);
        name_user.setText(intent.getStringExtra("surname") + " " + intent.getStringExtra("name") + " " + intent.getStringExtra("secondname"));

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(), ChangeProfileActivity.class);
                intent1.putExtra("name", intent.getStringExtra("name"));
                intent1.putExtra("surname", intent.getStringExtra("surname"));
                intent1.putExtra("secondname", intent.getStringExtra("secondname"));
                intent1.putExtra("phone", intent.getStringExtra("phone"));
                intent1.putExtra("mail", intent.getStringExtra("mail"));
                startActivity(intent1);
            }
        });
        MyAdapter2 adapter = new MyAdapter2(this, (ArrayList<ArrayList<String>>)intent.getSerializableExtra("review"));
        view_review.setAdapter(adapter);
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
