package youtube.demo.serverdiplom.AsyncTasks;

/**
 * Created by Cypher on 25.03.2017.
 */

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Requests.encodeParams;


public class LatLngToLatLng extends AsyncTask<String, Void, ArrayList<String>> {



    private ArrayList<String> mas = new ArrayList<>();
    @Override
    protected ArrayList<String> doInBackground(String... args) {
        try {

            final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
            final Map<String, String> params = Maps.newHashMap();
            params.put("sensor", "true");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
            params.put("latlng", args[0] + "," + args[1]);// адрес, который нужно геокодировать
            final String url = baseUrl + '?' + encodeParams(params) + "&key=AIzaSyCRefZ6rlVEma93HbiZnNUUvDbUAq3QUug";// генерируем путь с параметрами
            final JSONObject response;// делаем запрос к вебсервису и получаем от него ответ

            System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
            try {
                response = Requests.read(url);
                JSONObject location = response.getJSONArray("results").getJSONObject(0);
                System.out.println("LOCATION1= " + location);
                location = location.getJSONObject("geometry");
                System.out.println("LOCATION1= " + location);
                location = location.getJSONObject("location");
                System.out.println("LOCATION1= " + location);
                mas.ensureCapacity(4);
                mas.add(0,location.getString("lng"));// долгота
                mas.add(1,location.getString("lat"));// широта
                location = response.getJSONArray("results").getJSONObject(0);
                System.out.println("LOCATION1= " + location);
                System.out.println("LOCATION1= " + location.getString("formatted_address"));
                mas.add(2,location.getString("formatted_address"));
                System.out.println("Done");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mas;
    }

}
