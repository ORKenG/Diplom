package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.youtubedemo.Fragments.GmapFragment;
import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.JsonReader.encodeParams;

/**
 * Created by Cypher on 12.03.2017.
 */

public class CommentLoad extends AsyncTask<Integer, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(Integer... args) {
        ArrayList<String> result = new ArrayList<>();
        result.ensureCapacity(10);
        final Map<String, String> params = Maps.newHashMap();
        params.put("id", GmapFragment.current_id);
        String url_all_products = "http://7kmcosmetics.com/LoadComments.php";
        String final_URL = url_all_products + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = JsonReader.read(final_URL);
            JSONArray markers = json.getJSONArray("comments");
            // looping through All Products
            for (int i = 0; i < markers.length(); i++) {
                JSONObject c = markers.getJSONObject(i);
                String text = c.getString("name");
                text +=": ";
                text += c.getString("text");
                text += " : ";
                text += c.getString("date");
                result.add(i,text);

            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}