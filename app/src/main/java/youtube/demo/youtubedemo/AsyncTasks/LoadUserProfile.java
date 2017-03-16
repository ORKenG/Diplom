package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.Fragments.GmapFragment.current_user_id;
import static youtube.demo.youtubedemo.JsonReader.encodeParams;

/**
 * Created by Cypher on 19.01.2017.
 */

public class LoadUserProfile extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
    private static final String TAG_PRODUCTS = "info";
    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... args) {
        final Map<String, String> params = Maps.newHashMap();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        array.ensureCapacity(2);
        // getting JSON string from URL
        System.out.println("Done correctly");
        params.put("idu", current_user_id);
        String url_all_products = "http://7kmcosmetics.com/get_user_info.php";
        String final_URL = url_all_products + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = JsonReader.read(final_URL);
            JSONArray markers = json.getJSONArray(TAG_PRODUCTS);
            // looping through All Products
            for (int i = 0; i < markers.length(); i++) {
                ArrayList<String> line = new ArrayList<>();
                JSONObject c = markers.getJSONObject(i);
                String count = c.getString("count");
                String name = c.getString("name");
                String surname = c.getString("surname");
                line.add(0,count);
                line.add(1,name);
                line.add(2,surname);
                array.add(0,line);
            }
            markers = json.getJSONArray("avg");
            for (int i = 0; i < markers.length(); i++){
                ArrayList<String> line = new ArrayList<>();
                JSONObject c = markers.getJSONObject(i);
                String avg = c.getString("avg_mark");

                line.add(0,avg);
                array.add(1,line);
            }
            markers = json.getJSONArray("review");
            for (int i = 0; i < markers.length(); i++) {
                ArrayList<String> line = new ArrayList<>();
                JSONObject c = markers.getJSONObject(i);
                String txt = c.getString("txt");
                String from = c.getString("fromm");
                String fromid = c.getString("fromid");
                line.add(0,txt);
                line.add(1,from);
                line.add(2,fromid);
                array.add(i+2,line);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return array;
    }
}
