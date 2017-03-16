package youtube.demo.youtubedemo.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import youtube.demo.youtubedemo.JsonReader;

import static youtube.demo.youtubedemo.JsonReader.encodeParams;

/**
 * Created by Cypher on 20.02.2017.
 */

public class PlaceAutocomplete extends AsyncTask<String, Void, String[]> {
    private String[] mas = new String[4];
    @Override
    protected String[] doInBackground(String... args) {
        try {
            final String baseUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/json";// путь к Geocoding API по HTTP
            final Map<String, String> params = Maps.newHashMap();
            params.put("input", args[0]);// исходит ли запрос на геокодирование от устройства с датчиком местоположения
            params.put("types", "address");// адрес, который нужно геокодировать
            params.put("location", "46.452,30.733");// адрес, который нужно геокодировать
            params.put("components", "country:ua");// адрес, который нужно геокодировать
            params.put("radius", "8000");// адрес, который нужно геокодировать
            final String url = baseUrl + '?' + encodeParams(params) + "&key=AIzaSyCRefZ6rlVEma93HbiZnNUUvDbUAq3QUug";// генерируем путь с параметрами
            final JSONObject response;// делаем запрос к вебсервису и получаем от него ответ
            System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
            try {
                response = JsonReader.read(url);
                for (int i = 0; i <4 ; i++) {

                    if(response.getJSONArray("predictions").getJSONObject(i) != null){
                    JSONObject location = response.getJSONArray("predictions").getJSONObject(i);
                    System.out.println("LOCATION1= " + location);
                    location = location.getJSONObject("structured_formatting");
                    System.out.println("LOCATION1= " + location);
                    mas[i] = location.getString("main_text");
                    System.out.println(mas[i]);
                    System.out.println("Done");
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mas;
    }

}
