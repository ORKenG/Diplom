package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 11.01.2017.
 */

public class DeleteMarker extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... args) {
        // Building Parameters
        final Map<String, String> params = Maps.newHashMap();

        params.put("id", GmapFragment.current_id);

        // getting product details by making HTTP request
        String url_delete_product = "http://7kmcosmetics.com/delete_marker.php";
        String final_URL = url_delete_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = Requests.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // check your log for json response

        // json success tag


        return null;
    }
}
