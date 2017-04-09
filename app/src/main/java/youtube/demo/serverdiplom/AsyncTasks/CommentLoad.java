package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.JsonReader;

import static youtube.demo.serverdiplom.JsonReader.encodeParams;

/**
 * Created by Cypher on 12.03.2017.
 */

public class CommentLoad extends AsyncTask<Integer, Void, ArrayList<ArrayList<String>>> {
    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Integer... args) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
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
                ArrayList<String> line = new ArrayList<>();
                String name = c.getString("name");

               String text = c.getString("text");

                String date = c.getString("date");
                String idu = c.getString("idu");
                String photo = c.getString("photo");
               line.add(0,name);
               line.add(1,text);
               line.add(2,date);
               line.add(3,idu);
               line.add(4,photo);
                result.add(i,line);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}