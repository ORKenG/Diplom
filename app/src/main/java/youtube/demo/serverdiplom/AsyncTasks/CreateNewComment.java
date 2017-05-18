package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.Requests;

/**
 * Created by Cypher on 12.03.2017.
 */

public class CreateNewComment extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... args) {
        String text = args[0];
        final HashMap<String, String> params = Maps.newHashMap();
        params.put("text", text);
        params.put("id", GmapFragment.current_id);
        params.put("idu", GmapFragment.myId);

        // getting JSON Object
        // Note that create product url accepts POST method
        String url_create_comment = "http://7kmcosmetics.com/create_comment.php";

        try {
            String result = Requests.sendPostRequest(url_create_comment, params);
            JSONObject jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // check log cat fro response


        // check for success tag
        return null;
    }
}
