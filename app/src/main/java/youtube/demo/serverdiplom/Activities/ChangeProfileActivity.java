package youtube.demo.serverdiplom.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMap;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.Requests.encodeParams;
import static youtube.demo.serverdiplom.Requests.sendPostRequest;
import static youtube.demo.serverdiplom.Requests.uploadFile;

/**
 * Created by Cypher on 04.04.2017.
 */

public class ChangeProfileActivity extends AppCompatActivity {
    Button changeProfile;
    EditText name;
    EditText surname;
    EditText secondname;
    Button photo;
    EditText password;
    EditText password2;
    EditText phone;
    String[] args = new String[8];
    String[] args2 = new String[2];
    ImageView show_photo;
    Bitmap yourSelectedImage;
    private String selectedPath1;
    boolean flag = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();

                selectedPath1 = getPath(selectedImageUri);
                System.out.println("selectedPath1 : " + selectedPath1);
            show_photo.setImageURI(selectedImageUri);
            args2[0] = selectedPath1;
            String filename=selectedPath1.substring(selectedPath1.lastIndexOf("/")+1);
            args2[1] = filename;
            System.out.println(selectedPath1 + " filename=" + filename);
            flag = true;
        }
    }

    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_profile_activity);
        flagForMap = false;
        show_photo = (ImageView) findViewById(R.id.show_photo);
        changeProfile = (Button) findViewById(R.id.btn_register);
        name = (EditText) findViewById(R.id.enter_name);
        surname = (EditText) findViewById(R.id.enter_surname);
        secondname = (EditText) findViewById(R.id.enter_secondname);
        phone = (EditText) findViewById(R.id.enter_phonenubmer);
        password = (EditText) findViewById(R.id.enter_password);
        password2 = (EditText) findViewById(R.id.enter_password2);
        Intent intent = getIntent();
        photo = (Button) findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, 1);
            }
        });
        name.setText(intent.getStringExtra("name"));
        surname.setText(intent.getStringExtra("surname"));
        secondname.setText(intent.getStringExtra("secondname"));
        phone.setText(intent.getStringExtra("phone"));
        Picasso.with(this).load("http://7kmcosmetics.com/" + intent.getStringExtra("photo")).into(show_photo);
        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args[0] = name.getText().toString();
                args[1] = surname.getText().toString();
                args[2] = secondname.getText().toString();
                args[3] = password.getText().toString();
                args[4] = password2.getText().toString();
                args[5] = phone.getText().toString();
                args[6] = myId;

                System.out.println("args 3 =" + args[3] + " args 4 =" + args[4]);
                if (args[3].equals(args[4])) {
                    System.out.println(args[3] + " " + args[4]);
                    UpdateUser regTask = new UpdateUser();
                    regTask.execute(args);
                    if (flag) {
                        UploadPhoto photoTask = new UploadPhoto();
                        photoTask.execute(args2);
                    }

                } else {
                    System.out.println(args[3] + " " + args[4]);
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public class UploadPhoto extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... args) {
            uploadFile(args[1], args[0]);

            return null;
        }
    }

    public class UpdateUser extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... args) {
            // TODO: attempt authentication against a network service.
            final HashMap<String, String> params = Maps.newHashMap();
            // getting JSON string from URL
            params.put("password", args[3]);
            params.put("name", args[0]);
            params.put("surname", args[1]);
            params.put("secondname", args[2]);
            params.put("phone", args[5]);
            params.put("id", args[6]);
            String url_create_user = "http://7kmcosmetics.com/update_profile.php";
            String final_URL = url_create_user + "?" + encodeParams(params);
            System.out.println(final_URL);
            String result = sendPostRequest(url_create_user, params);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");

                if (success == 1) {
                    if (!args[3].equals("")) {
                        SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();

                        editor.putString("password", args[3]);
                        editor.apply();
                    }
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(result);


            return true;
        }
    }
}