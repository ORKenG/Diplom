package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import youtube.demo.youtubedemo.Fragments.GmapFragment;
import youtube.demo.youtubedemo.JSONParser;
import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.JsonReader.encodeParams;

/**
 * Created by Cypher on 12.03.2017.
 */

public class CreateNewComment extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... args) {
        String text = args[0];
        final Map<String, String> params = Maps.newHashMap();
        params.put("text", text);
        params.put("id", GmapFragment.current_id);
        params.put("idu", LoadAllProducts.myId);

        // getting JSON Object
        // Note that create product url accepts POST method
        String url_create_product = "http://7kmcosmetics.com/create_comment.php";
        String final_URL = url_create_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = JsonReader.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // check log cat fro response


        // check for success tag
        return null;
    }
}
