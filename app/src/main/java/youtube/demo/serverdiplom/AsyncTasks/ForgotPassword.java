package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;

import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 5/14/2017.
 */

public class ForgotPassword extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... args) {
        final HashMap<String, String> params = Maps.newHashMap();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        array.ensureCapacity(2);
        // getting JSON string from URL
        System.out.println("Done correctly");
        params.put("number", "38" + args[0]);
        String forgot_password = "http://7kmcosmetics.com/forgot_password.php";
        String final_URL = forgot_password + "?" + encodeParams(params);
        System.out.println(final_URL);
        String result  = Requests.sendPostRequest(forgot_password, params);
        System.out.println("code = " + result);
        return null;
    }
}
