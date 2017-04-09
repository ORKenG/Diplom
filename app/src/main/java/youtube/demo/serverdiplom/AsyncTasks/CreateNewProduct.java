package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.JsonReader;

import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_id;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.flagForChange;
import static youtube.demo.serverdiplom.JsonReader.encodeParams;

/**
 * Created by Cypher on 08.01.2017.
 */
public class CreateNewProduct extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected String doInBackground(String... args) {
        String name = args[0];
        String position_x = args[1];
        String position_y = args[2];
        String type = args[3];
        String phone = args[4];
        String real_posistion_x = args[5];
        String real_posistion_y = args[6];
        String address = args[7];
        String price = args[8];

        // Building Parameters
        final Map<String, String> params = Maps.newHashMap();
        params.put("name", name);
        params.put("position_x", position_x);
        params.put("position_y", position_y);
        params.put("type", type);
        params.put("phone", phone);
        params.put("idu", GmapFragment.myId);
        params.put("real_position_x", real_posistion_x);
        params.put("real_position_y", real_posistion_y);
        params.put("address", address);
        params.put("price", price);
        params.put("id", current_id);
        String url_create_product;
        if (!flagForChange) {
             url_create_product = "http://7kmcosmetics.com/create_product.php";
        } else {
            url_create_product = "http://7kmcosmetics.com/update_product.php";
        }
        String final_URL = url_create_product + "?" + encodeParams(params);
        JSONObject json;
        try {
            System.out.println(final_URL);
            json = JsonReader.read(final_URL);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        flagForChange = false;
        return null;
    }


}