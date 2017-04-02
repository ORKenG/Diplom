package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import youtube.demo.serverdiplom.JSONParser;
import youtube.demo.serverdiplom.JsonReader;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.JsonReader.encodeParams;

public class RegistrationActivity extends AppCompatActivity {

    Button reg;
    EditText name;
    EditText surname;
    EditText secondname;
    EditText mail;
    EditText password;
    EditText password2;
    EditText phone;
    String[] args = new String[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        reg = (Button) findViewById(R.id.btn_register);
        name = (EditText) findViewById(R.id.enter_name);
        surname = (EditText) findViewById(R.id.enter_surname);
        secondname = (EditText) findViewById(R.id.enter_secondname);
        phone = (EditText) findViewById(R.id.enter_phonenubmer);
        password = (EditText) findViewById(R.id.enter_password);
        password2 = (EditText) findViewById(R.id.enter_password2);
        mail = (EditText) findViewById(R.id.enter_Email);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                args[0] = name.getText().toString();
                args[1] = surname.getText().toString();
                args[2] = secondname.getText().toString();
                args[3] = password.getText().toString();
                args[4] = password2.getText().toString();
                args[5] = phone.getText().toString();
                args[6] = mail.getText().toString();
                System.out.println("args 3 =" + args[3] + " args 4 =" + args[4]);
                if (args[3].equals(args[4])){
                    System.out.println(args[3] + " " + args[4]);
                UserRegisterTask regTask = new UserRegisterTask();
                regTask.execute(args);
                }
                else {
                    System.out.println(args[3] + " " + args[4]);
                    Toast.makeText(getApplicationContext(),"Пароли не совпадают",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class UserRegisterTask extends AsyncTask<String, Void, Boolean> {

        private JSONParser jParser = new JSONParser();



        @Override
        protected Boolean doInBackground(String... args) {
            // TODO: attempt authentication against a network service.
            final Map<String, String> params = Maps.newHashMap();
            // getting JSON string from URL
            params.put("mail", args[6]);
            params.put("password", args[3]);
            params.put("name", args[0]);
            params.put("surname", args[1]);
            params.put("secondname", args[2]);
            params.put("phone", args[5]);
            String url_create_user = "http://7kmcosmetics.com/create_user.php";
            String final_URL = url_create_user + "?" + encodeParams(params);
            JSONObject json;
            try {
                // Simulate network access.
                json = JsonReader.read(final_URL);
                int success = json.getInt("success");

                if (success == 1) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }
    }
}
