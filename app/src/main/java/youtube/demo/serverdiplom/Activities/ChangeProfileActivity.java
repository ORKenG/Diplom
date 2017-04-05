package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.JsonReader;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMap;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.JsonReader.encodeParams;

/**
 * Created by Cypher on 04.04.2017.
 */

public class ChangeProfileActivity extends AppCompatActivity {
    Button changeProfile;
    EditText name;
    EditText surname;
    EditText secondname;

    EditText password;
    EditText password2;
    EditText phone;
    String[] args = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_profile_activity);
        flagForMap = false;
        changeProfile = (Button) findViewById(R.id.btn_register);
        name = (EditText) findViewById(R.id.enter_name);
        surname = (EditText) findViewById(R.id.enter_surname);
        secondname = (EditText) findViewById(R.id.enter_secondname);
        phone = (EditText) findViewById(R.id.enter_phonenubmer);
        password = (EditText) findViewById(R.id.enter_password);
        password2 = (EditText) findViewById(R.id.enter_password2);
        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        surname.setText(intent.getStringExtra("surname"));
        secondname.setText(intent.getStringExtra("secondname"));
        phone.setText(intent.getStringExtra("phone"));
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
            final Map<String, String> params = Maps.newHashMap();
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
            JSONObject json;
            try {
                // Simulate network access.
                json = JsonReader.read(final_URL);
                int success = json.getInt("success");

                if (success == 1) {
                    if (args[3]!="") {
                        SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();

                        editor.putString("password",args[3]);
                        editor.apply();
                    }
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


            return true;
        }
    }
}