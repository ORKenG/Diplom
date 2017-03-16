package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.youtubedemo.Fragments.GmapFragment;
import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.JsonReader.encodeParams;

/**
 * Created by Cypher on 13.03.2017.
 */

public class CreateNewReview extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... args) {
        String text = args[0];
        final Map<String, String> params = Maps.newHashMap();
        params.put("text", text);
        params.put("idu_to", GmapFragment.current_user_id);
        params.put("idu_from", LoadAllProducts.myId);
        params.put("mark", args[1]);

        // getting JSON Object
        // Note that create product url accepts POST method
        String url_create_product = "http://7kmcosmetics.com/create_review.php";
        String final_URL = url_create_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = JsonReader.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
