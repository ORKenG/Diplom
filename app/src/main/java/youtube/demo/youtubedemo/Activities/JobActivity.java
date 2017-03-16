package youtube.demo.youtubedemo.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.youtubedemo.AsyncTasks.CommentLoad;
import youtube.demo.youtubedemo.AsyncTasks.CreateNewComment;
import youtube.demo.youtubedemo.AsyncTasks.DeleteMarker;
import youtube.demo.youtubedemo.Fragments.GmapFragment;
import youtube.demo.youtubedemo.AsyncTasks.LoadUserProfile;
import youtube.demo.youtubedemo.MyAdapter;
import youtube.demo.youtubedemo.R;

import static youtube.demo.youtubedemo.Activities.MainActivity.flag;

/**
 * Created by Cypher on 04.12.2016.
 */

public class JobActivity extends AppCompatActivity {

    TextView job_name;
    TextView phone;
    FloatingActionButton call;
    FloatingActionButton sms;
    Button delete;
    Button show_profile;
    ListView comment;
    String phoneNumber = "";
    EditText commentText;
    Button sendComment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent bundle = getIntent();
        setContentView(R.layout.activity_job);
        System.out.println("Current id = " + GmapFragment.current_id);
        phoneNumber = bundle.getStringExtra("phone");

        ArrayList<ArrayList<String>> name = new ArrayList<>();
        CommentLoad commentLoad = new CommentLoad();
        commentLoad.execute();
        try {
            name = commentLoad.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        comment = (ListView) findViewById(R.id.view_comment);
        MyAdapter adapter = new MyAdapter(this, name);
        comment.setAdapter(adapter);
        commentText = (EditText) findViewById(R.id.commentInput);
        sendComment = (Button) findViewById(R.id.sendComment);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        phone = ((TextView) findViewById(R.id.employee_phone));
        phone.setText("Номер телефона: " + phoneNumber + "\nАдрес: " + bundle.getStringExtra("address"));
        job_name = ((TextView) findViewById(R.id.job_title));
        job_name.setText(bundle.getStringExtra("name"));
        call = ((FloatingActionButton) findViewById(R.id.call));
        sms = ((FloatingActionButton) findViewById(R.id.sms));
        delete = ((Button) findViewById(R.id.delete));
        show_profile = ((Button) findViewById(R.id.show_profile));
        ViewGroup layout = (ViewGroup) delete.getParent();
        if (!flag) {
            layout.removeView(delete);
        } else {
            layout.removeView(show_profile);
        layout.removeViewInLayout(findViewById(R.id.linear));
        }
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(phone.getText().toString());
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms(phone.getText().toString());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        show_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    showProfile();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void sendComment(){
        String[] args = new String[1];
        args[0] = commentText.getText().toString();
        CreateNewComment createNewComment = new CreateNewComment();
        createNewComment.execute(args);
        CommentLoad commentLoad = new CommentLoad();
        commentLoad.execute();
        ArrayList<ArrayList<String>> name = new ArrayList<>();
        try {
            name = commentLoad.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        MyAdapter adapter = new MyAdapter(this, name);
        comment.setAdapter(adapter);

    }

    public void showProfile() throws ExecutionException, InterruptedException {
        LoadUserProfile l = new LoadUserProfile();
        l.execute();
        ArrayList<ArrayList<String>> counts = new ArrayList<>();
        counts.ensureCapacity(4);
               counts = l.get();
        Intent intent = new Intent(this, User_Profile.class);
        intent.putExtra("count",counts.get(0).get(0));
        intent.putExtra("name",counts.get(0).get(1));
        intent.putExtra("surname",counts.get(0).get(2));
        intent.putExtra("avg",counts.get(1).get(0));
       ArrayList<String> counts2 = new ArrayList<>();
       ArrayList<String> id = new ArrayList<>();
        counts2.ensureCapacity(4);
        for (int i = 2; i<counts.size(); i++){
            counts2.add(i-2,counts.get(i).get(1) + ": " + counts.get(i).get(0));
            id.add(i-2,counts.get(i).get(2));
        }
        intent.putExtra("review",counts2);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void delete() {
        DeleteMarker del = new DeleteMarker();
        del.execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void call(String phone) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(dialIntent);
    }

    public void sms(String phone) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("sms_body", "Some SMS text");
        smsIntent.putExtra("address", phone);
        startActivity(smsIntent);
    }

}
