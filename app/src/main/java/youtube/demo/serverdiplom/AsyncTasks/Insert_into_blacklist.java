package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_user_id;
import static youtube.demo.serverdiplom.Requests.encodeParams;

/**
 * Created by Cypher on 18.03.2017.
 */

public class Insert_into_blacklist extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... args) {

        final Map<String, String> params = Maps.newHashMap();
        params.put("idu2", current_user_id);
        params.put("idu1", GmapFragment.myId);

        // getting JSON Object
        // Note that create product url accepts POST method
        String url_create_product = "http://7kmcosmetics.com/insert_into_blacklist.php";
        String final_URL = url_create_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            json = Requests.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
