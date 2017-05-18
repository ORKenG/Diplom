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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.Verify;
import youtube.demo.serverdiplom.Requests;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Requests.encodeParams;

public class RegistrationActivity extends AppCompatActivity {

    Button reg;
    EditText name;
    EditText surname;
    EditText secondname;
    EditText password;
    EditText password2;
    EditText phone;
    String[] args = new String[7];
    EditText pin;
    Button acceptPin;
    String pinCode = null;

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
        pin = (EditText) findViewById(R.id.pin);
        acceptPin = (Button) findViewById(R.id.accept);
        acceptPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    if (pinCode.equals(pin.getText().toString())){
                        UserRegisterTask regTask = new UserRegisterTask(RegistrationActivity.this);
                        regTask.execute(args);
                    } else {
                        Toast.makeText(getApplicationContext(), "Неверный Pin", Toast.LENGTH_LONG).show();
                    }


            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if (!name.getText().toString().equals("") && !surname.getText().toString().equals("") && !secondname.getText().toString().equals("") && !phone.getText().toString().equals("")) {
                                           args[0] = name.getText().toString();
                                           args[1] = surname.getText().toString();
                                           args[2] = secondname.getText().toString();
                                           args[3] = password.getText().toString();
                                           args[4] = password2.getText().toString();
                                           if (phone.getText().toString().length() == 10)
                                           args[5] = phone.getText().toString();
                                            else
                                           Toast.makeText(RegistrationActivity.this.getApplicationContext(), "Введите корректный номер телефона", Toast.LENGTH_LONG).show();
                                           System.out.println("args 3 =" + args[3] + " args 4 =" + args[4]);
                                           if (args[3].equals(args[4])) {
                                               System.out.println(args[3] + " " + args[4]);

                                               Verify verify = new Verify();
                                               verify.execute(args[5]);

                                               try {
                                                   pinCode = verify.get();
                                               } catch (InterruptedException | ExecutionException e) {
                                                   e.printStackTrace();
                                               }
                                               acceptPin.setEnabled(true);
                                               pin.setEnabled(true);
                                           } else {
                                               System.out.println(args[3] + " " + args[4]);
                                               Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                                           }
                                       } else {
                                           Toast.makeText(getApplicationContext(), "Введите все данные", Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }

        );
    }


    public class UserRegisterTask extends AsyncTask<String, Void, Boolean> {
        RegistrationActivity parent;
        String message;

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(parent.getBaseContext(), message, Toast.LENGTH_LONG).show();
        }

        UserRegisterTask(RegistrationActivity parent){
            this.parent = parent;
        }

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
            String url_create_user = "http://7kmcosmetics.com/create_user.php";
            String final_URL = url_create_user + "?" + encodeParams(params);
            try {
                System.out.println(final_URL);
                String result  = Requests.sendPostRequest(url_create_user, params);
                JSONObject jsonObject = new JSONObject(result);
                int success = jsonObject.getInt("success");

                if (success == 1) {
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    message = jsonObject.getString("message");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

    }
}
