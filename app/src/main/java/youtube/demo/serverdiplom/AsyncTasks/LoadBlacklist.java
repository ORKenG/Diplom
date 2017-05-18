package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 19.03.2017.
 */

public class LoadBlacklist extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... args) {
        final Map<String, String> params = Maps.newHashMap();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        array.ensureCapacity(2);
        // getting JSON string from URL
        System.out.println("Done correctly");
        params.put("idu1", myId);
        String url_all_products = "http://7kmcosmetics.com/get_blacklist.php";
        String final_URL = url_all_products + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = Requests.read(final_URL);
            JSONArray markers = json.getJSONArray("blacklist");
            // looping through All Products
            for (int i = 0; i < markers.length(); i++) {
                JSONObject c = markers.getJSONObject(i);
                ArrayList<String> line = new ArrayList<>();
                // Storing each json item in variable
                String name = c.getString("name");
                String surname = c.getString("surname");
                String id = c.getString("id");
                String photo = c.getString("photo");
                line.add(0,name);
                line.add(1,surname);
                line.add(2,id);
                line.add(3,photo);
                array.add(line);
                System.out.println("TESTTEST=" + name);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
}
