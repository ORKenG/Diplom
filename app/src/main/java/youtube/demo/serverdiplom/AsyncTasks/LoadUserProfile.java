package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.JsonReader;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMyProfile;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_user_id;
import static youtube.demo.serverdiplom.JsonReader.encodeParams;

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
        if (!flagForMyProfile) {
            params.put("idu", current_user_id);
        }
        else{
            params.put("idu", myId);
        }
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
                String secondname = c.getString("secondname");
                String phone = c.getString("phone");
                String mail = c.getString("mail");
                line.add(0,count);
                line.add(1,name);
                line.add(2,surname);
                line.add(3,secondname);
                line.add(4,phone);
                line.add(5,mail);
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
                String date = c.getString("date");
                String mark = c.getString("mark");
                line.add(0,txt);
                line.add(1,from);
                line.add(2,fromid);
                line.add(3,date);
                line.add(4,mark);
                array.add(i+2,line);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return array;
    }
}
