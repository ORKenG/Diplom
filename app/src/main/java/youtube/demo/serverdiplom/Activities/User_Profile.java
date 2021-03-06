package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.CreateNewReview;
import youtube.demo.serverdiplom.AsyncTasks.Insert_into_blacklist;
import youtube.demo.serverdiplom.AsyncTasks.LoadUserProfile;
import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.AdapterForReview;
import youtube.demo.serverdiplom.R;

public class User_Profile extends AppCompatActivity {

    TextView job_count;
    TextView name_user;
    TextView surname_user;
    TextView avg_mark;
    ListView view_review;
    EditText inputReview;
    Button sendReview;
    Button addToBlacklist;
    NumberPicker mark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        addToBlacklist = (Button) findViewById(R.id.addToBlacklist);
        inputReview = (EditText) findViewById(R.id.input_review);
        sendReview = (Button) findViewById(R.id.sendReview);
        sendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview();
            }
        });
        view_review = (ListView) findViewById(R.id.view_review);
        mark = (NumberPicker) findViewById(R.id.mark);
        mark.setMaxValue(5);
        mark.setMinValue(1);

        avg_mark = (TextView) findViewById(R.id.avg_mark);
        job_count = (TextView) findViewById(R.id.job_count);
        name_user = (TextView) findViewById(R.id.name_user);
        surname_user = (TextView) findViewById(R.id.surname_user);

        Intent intent = getIntent();
        ArrayList<String> id = intent.getStringArrayListExtra("id");
        final ViewGroup layout = (ViewGroup) avg_mark.getParent();
        for (int i=0; i<id.size(); i++){
            if (id.get(i).equals(GmapFragment.myId)){
                layout.removeView(inputReview);
                layout.removeView(sendReview);
                layout.removeView(mark);
                i=id.size();
            }
        }

        addToBlacklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert_into_blacklist blacklist = new Insert_into_blacklist();
                blacklist.execute();
                layout.removeView(addToBlacklist);
            }
        });
        String txt = "Number of jobs added: " + intent.getStringExtra("count");
        job_count.setText(txt);
        String txt2 = "Рейтинг пользователя: " + intent.getStringExtra("avg");
        avg_mark.setText(txt2);
        name_user.setText(intent.getStringExtra("name"));
        surname_user.setText(intent.getStringExtra("surname"));

        AdapterForReview adapter = new AdapterForReview(this, (ArrayList<ArrayList<String>>)intent.getSerializableExtra("review"));
        view_review.setAdapter(adapter);


    }
    private void sendReview(){
        String[] args = new String[2];
        args[0] = inputReview.getText().toString();
        args[1] = String.valueOf(mark.getValue());
        CreateNewReview createNewReview = new CreateNewReview();
        createNewReview.execute(args);
        LoadUserProfile l = new LoadUserProfile();
        l.execute();

        ArrayList<ArrayList<String>> counts = new ArrayList<>();
        counts.ensureCapacity(4);
        try {
            counts = l.get();
            ArrayList<ArrayList<String>> counts2 = new ArrayList<>();

            counts2.ensureCapacity(4);
            for (int i = 2; i<counts.size(); i++){
                ArrayList<String> line = new ArrayList<>();
                line.add(0, counts.get(i).get(1));
                line.add(1, counts.get(i).get(0));
                line.add(2, counts.get(i).get(3));
                line.add(3, counts.get(i).get(4));
                line.add(4, counts.get(i).get(2));
                line.add(5, counts.get(i).get(5));
                counts2.add(i-2,line);
            }
           AdapterForReview adapter = new AdapterForReview(this, counts2);
            view_review.setAdapter(adapter);
            String txt2 = "Рейтинг пользователя: " + counts.get(1).get(0);
            avg_mark.setText(txt2);
            ViewGroup layout = (ViewGroup) avg_mark.getParent();
            layout.removeView(inputReview);
            layout.removeView(sendReview);
            layout.removeView(mark);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}
