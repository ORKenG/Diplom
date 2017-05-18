package youtube.demo.serverdiplom.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.Maps;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.AsyncTasks.ForgotPassword;
import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.Requests;
import youtube.demo.serverdiplom.R;

import static youtube.demo.serverdiplom.Requests.encodeParams;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button singInButton = (Button) findViewById(R.id.email_sign_in_button);
        singInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Button forgot_passwordButon = (Button) findViewById(R.id.forgot_password);
        forgot_passwordButon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPhoneView.getText().toString().length() == 10) {
                    ForgotPassword forgotPassword = new ForgotPassword();
                    forgotPassword.execute(mPhoneView.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Введите свой номер телефона", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button regButton = (Button) findViewById(R.id.register_button);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegPage();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        System.out.println("username = " + sharedPreferences.getString("login","default"));
        if (!sharedPreferences.getString("login","default").equals("default")){
            showProgress(true);
            mAuthTask = new UserLoginTask(sharedPreferences.getString("login","default"), sharedPreferences.getString("password","default"));
            mAuthTask.execute();
        }
    }

    private void goToRegPage() {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        mPhoneView.setError(null);
        mPasswordView.setError(null);

        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(phone, password);
            mAuthTask.execute();
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mPhone;
        private final String mPassword;

        UserLoginTask(String phone, String password) {
            mPhone = phone;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... args) {
            int success = 0;
            final Map<String, String> params = Maps.newHashMap();
            // getting JSON string from URL
            System.out.println("Done correctly: " + mPhone + mPassword);
            params.put("phone", mPhone);
            params.put("password", mPassword);
            String url_all_products = "http://7kmcosmetics.com/diplom_login.php";
            String final_URL = url_all_products + "?" + encodeParams(params);
            JSONObject json;

            try {
                json = Requests.read(final_URL);
                // Simulate network access.
                success = json.getInt("success");
                System.out.println("s1=" +success);

                if (success == 1) {
                    GmapFragment.myId = json.getString("idu");
                    GmapFragment.userPhone = json.getString("phone");
                    SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", mPhone);
                    editor.putString("password", mPassword);
                    editor.apply();
                    System.out.println("Success = " + success + "myId = " + GmapFragment.myId + " myPhone = " + GmapFragment.userPhone);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }

            System.out.println("s2=" +success);
            return success;
        }

        @Override
        protected void onPostExecute(Integer success) {
            mAuthTask = null;
            showProgress(false);
            System.out.println("s3=" +success);
            if (success!=1){
                Toast.makeText(getBaseContext(), "Неверный E-mail или пароль", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

