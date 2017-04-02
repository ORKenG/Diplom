package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.Activities.MainActivity;
import youtube.demo.serverdiplom.JsonReader;

import static youtube.demo.serverdiplom.AsyncTasks.LoadAllProducts.myId;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_Lat;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_Lng;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.stringForSearch;
import static youtube.demo.serverdiplom.JsonReader.encodeParams;

public class LoadProductsOnCurrentLatLng extends AsyncTask<Object, Object, ArrayList<ArrayList<String>>> {
    private static final String TAG_PRODUCTS = "marker";
    private static final String TAG_ID = "id";
    private static final String TAG_X = "position_x";
    private static final String TAG_Y = "position_y";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_NAME = "name";
    private static final String TAG_TYPE = "type";
    private static final String TAG_USER_ID = "idu";

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
        params.put("position_x", current_Lat);
        params.put("position_y", current_Lng);
        params.put("flag", String.valueOf(MainActivity.flag));
        if (!stringForSearch.equals("")){
            params.put("name", stringForSearch);
        }
        String url_all_products = "http://7kmcosmetics.com/get_markers2.php";
        String final_URL = url_all_products + "?" + encodeParams(params);
        JSONObject json;

        try {
            System.out.println("Currentlat=" + current_Lat + " currentlng=" + current_Lng + " flag=" + String.valueOf(MainActivity.flag) + " myid=" + myId);
            json = JsonReader.read(final_URL);
            System.out.println(final_URL);
            JSONArray markers = json.getJSONArray(TAG_PRODUCTS);
            // looping through All Products
            for (int i = 0; i < markers.length(); i++) {
                JSONObject c = markers.getJSONObject(i);
                ArrayList<String> line = new ArrayList<>();
                // Storing each json item in variable
                String name = c.getString(TAG_NAME);
                String position_x = c.getString(TAG_X);
                String position_y = c.getString(TAG_Y);
                String phone = c.getString(TAG_PHONE);
                String type = c.getString(TAG_TYPE);
                String id = c.getString(TAG_ID);
                String idu = c.getString(TAG_USER_ID);
                String price = c.getString("price");
                String address = c.getString("address");
                String real_position_x = c.getString("real_position_x");
                String real_position_y = c.getString("real_position_y");

                line.add(0,name);
                line.add(1,position_x);
                line.add(2,position_y);
                line.add(3,phone);
                line.add(4,type);
                line.add(5,id);
                line.add(6,idu);
                line.add(7,price);
                line.add(8,address);
                line.add(9,real_position_x);
                line.add(10,real_position_y);
                array.add(line);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return array;
    }

}