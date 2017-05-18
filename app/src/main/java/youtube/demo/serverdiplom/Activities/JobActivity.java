package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.CommentLoad;
import youtube.demo.serverdiplom.AsyncTasks.CreateNewComment;
import youtube.demo.serverdiplom.AsyncTasks.DeleteMarker;
import youtube.demo.serverdiplom.AsyncTasks.LoadUserProfile;
import youtube.demo.serverdiplom.AdapterForComments;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Activities.MainActivity.flag;
import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMyProfile;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_user_id;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.flagForChange;

/**
 * Created by Cypher on 04.12.2016.
 */

public class JobActivity extends AppCompatActivity {

    TextView job_name;
    TextView phone;
    TextView price;
    FloatingActionButton call;
    FloatingActionButton sms;
    Button delete;
    Button show_profile;
    ListView comment;
    public static String user_id;
    EditText commentText;
    Button sendComment;
    TextView address;
    Button change;
    public static String textForTitle;
    public static String textForPhone;
    public static String textForAddress;
    public static String textForPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent bundle = getIntent();
        setContentView(R.layout.activity_job);
        ArrayList<ArrayList<String>> name = new ArrayList<>();
        CommentLoad commentLoad = new CommentLoad();
        commentLoad.execute();
        try {
            name = commentLoad.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (user_id.equals("")) {
            user_id = current_user_id;
        } else {
            current_user_id = user_id;
        }
        comment = (ListView) findViewById(R.id.view_comment);
        AdapterForComments adapter = new AdapterForComments(this, name);
        comment.setAdapter(adapter);
        price = (TextView) findViewById(R.id.price);
        commentText = (EditText) findViewById(R.id.commentInput);
        sendComment = (Button) findViewById(R.id.sendComment);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        phone = (TextView) findViewById(R.id.employee_phone);
        address = (TextView) findViewById(R.id.address);
        job_name = ((TextView) findViewById(R.id.job_title));
        if (textForTitle.equals("") && textForPhone.equals("")) {
            textForPhone = bundle.getStringExtra("phone");
            textForAddress = bundle.getStringExtra("address");
            textForTitle = bundle.getStringExtra("name");
            textForPrice = bundle.getStringExtra("price");
        }
        phone.setText(textForPhone);
        address.setText(textForAddress);
        job_name.setText(textForTitle);
        price.setText("Вознаграждение: " + textForPrice + " грн.");
        change = (Button) findViewById(R.id.change);
        call = ((FloatingActionButton) findViewById(R.id.call));
        sms = ((FloatingActionButton) findViewById(R.id.sms));
        delete = ((Button) findViewById(R.id.delete));
        show_profile = ((Button) findViewById(R.id.show_profile));
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                flagForChange = true;

                intent.putExtra("address",bundle.getStringExtra("address"));
                intent.putExtra("name",bundle.getStringExtra("name"));
                intent.putExtra("price",bundle.getStringExtra("price"));
                startActivity(intent);
            }
        });
        System.out.println("flag = " + flag);
        ViewGroup layout = (ViewGroup) job_name.getParent();
        if (!flag) {

            delete.setVisibility(View.GONE);

            change.setVisibility(View.GONE);
        } else {
            show_profile.setVisibility(View.GONE);
            findViewById(R.id.linear).setVisibility(View.GONE);

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

    private void sendComment() {
        String[] args = new String[1];
        args[0] = commentText.getText().toString();
        CreateNewComment createNewComment = new CreateNewComment();
        createNewComment.execute(args);
        CommentLoad commentLoad = new CommentLoad();
        commentLoad.execute();
        commentText.setText("");
        ArrayList<ArrayList<String>> name = new ArrayList<>();
        try {
            name = commentLoad.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        AdapterForComments adapter = new AdapterForComments(this, name);
        comment.setAdapter(adapter);

    }

    public void showProfile() throws ExecutionException, InterruptedException {
        flagForMyProfile = false;
        LoadUserProfile l = new LoadUserProfile();
        l.execute();
        ArrayList<ArrayList<String>> counts = new ArrayList<>();
        counts.ensureCapacity(5);
        counts = l.get();
        Intent intent = new Intent(this, User_Profile.class);
        intent.putExtra("count", counts.get(0).get(0));
        intent.putExtra("name", counts.get(0).get(1));
        intent.putExtra("surname", counts.get(0).get(2));
        intent.putExtra("avg", counts.get(1).get(0));
        ArrayList<ArrayList<String>> counts2 = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();
        counts2.ensureCapacity(5);
        for (int i = 2; i < counts.size(); i++) {
            ArrayList<String> line = new ArrayList<>();
            line.add(0, counts.get(i).get(1));
            line.add(1, counts.get(i).get(0));
            line.add(2, counts.get(i).get(3));
            line.add(3, counts.get(i).get(4));
            line.add(4, counts.get(i).get(2));
            line.add(5, counts.get(i).get(5));
            counts2.add(i - 2, line);
            id.add(i - 2, counts.get(i).get(2));
        }
        intent.putExtra("review", counts2);
        intent.putExtra("id", id);
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
