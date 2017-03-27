package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.youtubedemo.Activities.MainActivity;
import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.JsonReader.encodeParams;

public class LoadAllProducts extends AsyncTask<Object, Object, ArrayList<ArrayList<String>>> {

    public static String myId="";
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    /**
     * getting All markers from url
     * */
    protected ArrayList<ArrayList<String>> doInBackground(Object... args) {
        // Building Parameters
        final Map<String, String> params = Maps.newHashMap();
        ArrayList<ArrayList<String>> array = new ArrayList<>();
        array.ensureCapacity(2);
        // getting JSON string from URL
        System.out.println("Done correctly");
        params.put("idu", myId);
        params.put("flag", String.valueOf(MainActivity.flag));
        String url_all_products = "http://7kmcosmetics.com/get_markers.php";
        String final_URL = url_all_products + "?" + encodeParams(params);
        JSONObject json;

        try {
            json = JsonReader.read(final_URL);
            JSONArray markers = json.getJSONArray("marker");
                // looping through All Products
                for (int i = 0; i < markers.length(); i++) {
                    JSONObject c = markers.getJSONObject(i);
                    ArrayList<String> line = new ArrayList<>();
                    // Storing each json item in variable

                    String position_x = c.getString("position_x");
                    String position_y = c.getString("position_y");
                    String count = c.getString("idcount");
                    String address = c.getString("address");

                    line.add(0,count);
                    line.add(1,position_x);
                    line.add(2,position_y);
                    line.add(3,address);
                    array.add(line);

                }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return array;
    }

}