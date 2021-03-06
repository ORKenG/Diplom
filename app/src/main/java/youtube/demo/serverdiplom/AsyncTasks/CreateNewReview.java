package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 13.03.2017.
 */

public class CreateNewReview extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... args) {
        String text = args[0];
        final HashMap<String, String> params = Maps.newHashMap();
        params.put("text", text);
        params.put("idu_to", GmapFragment.current_user_id);
        params.put("idu_from", GmapFragment.myId);
        params.put("mark", args[1]);

        // getting JSON Object
        // Note that create product url accepts POST method
        String url_create_product = "http://7kmcosmetics.com/create_review.php";
        String final_URL = url_create_product + "?" + encodeParams(params);
        try {
            System.out.println(final_URL);
            String result  = Requests.sendPostRequest(url_create_product, params);
            JSONObject jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
