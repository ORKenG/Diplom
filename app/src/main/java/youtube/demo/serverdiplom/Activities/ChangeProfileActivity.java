package youtube.demo.serverdiplom.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import youtube.demo.serverdiplom.JsonReader;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMap;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.JsonReader.encodeParams;
import static youtube.demo.serverdiplom.JsonReader.sendPostRequest;

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
    ImageView show_photo;
    Bitmap yourSelectedImage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = getBaseContext().getContentResolver().openInputStream(data.getData());
                yourSelectedImage = BitmapFactory.decodeStream(inputStream);
                show_photo.setImageBitmap(yourSelectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, u
        }
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
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, 1);
            }
        });
        name.setText(intent.getStringExtra("name"));
        surname.setText(intent.getStringExtra("surname"));
        secondname.setText(intent.getStringExtra("secondname"));
        phone.setText(intent.getStringExtra("phone"));
        String forImage = intent.getStringExtra("photo");
        byte[] decodedString = Base64.decode(forImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        show_photo.setImageBitmap(decodedByte);
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte [] byte_arr = stream.toByteArray();
                String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                args[7] = image_str;
                System.out.println("args 3 =" + args[3] + " args 4 =" + args[4]);
                if (args[3].equals(args[4])){
                    System.out.println(args[3] + " " + args[4]);
                    UpdateUser regTask = new UpdateUser();
                    regTask.execute(args);
                }
                else {
                    System.out.println(args[3] + " " + args[4]);
                    Toast.makeText(getApplicationContext(),"Пароли не совпадают",Toast.LENGTH_LONG).show();
                }

            }
        });


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
            params.put("image", args[7]);
            System.out.println("ing" + args[7]);
            String url_create_user = "http://7kmcosmetics.com/update_profile.php";
            String final_URL = url_create_user + "?" + encodeParams(params);
            System.out.println(final_URL);
            String result = sendPostRequest(url_create_user,params);
            try {
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");

                if (success == 1) {
                    if (!args[3].equals("")) {
                        SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();

                        editor.putString("password",args[3]);
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