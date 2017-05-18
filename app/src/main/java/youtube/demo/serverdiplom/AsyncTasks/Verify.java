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

public class Verify extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... args) {
        final HashMap<String, String> params = Maps.newHashMap();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        array.ensureCapacity(2);
        // getting JSON string from URL
        System.out.println("Done correctly");
        params.put("number", "38" + args[0]);
        String url_verify = "http://7kmcosmetics.com/verify.php";
        String final_URL = url_verify + "?" + encodeParams(params);
        System.out.println(final_URL);
        String result  = Requests.sendPostRequest(url_verify, params);
        System.out.println("code = " + result);
        return result;
    }
}