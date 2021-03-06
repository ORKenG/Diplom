package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;
import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 19.03.2017.
 */

public class Delete_from_blacklist extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... args) {
        final Map<String, String> params = Maps.newHashMap();
        String idu2 = args[0];
        params.put("idu1", myId);
        params.put("idu2", idu2);

        // getting product details by making HTTP request
        String url_delete_product = "http://7kmcosmetics.com/remove_from_blacklist.php";
        String final_URL = url_delete_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = Requests.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
