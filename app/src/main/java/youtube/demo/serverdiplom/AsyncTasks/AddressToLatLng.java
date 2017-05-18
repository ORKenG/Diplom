package youtube.demo.serverdiplom.AsyncTasks;

import android.os.AsyncTask;
import android.support.test.espresso.core.deps.guava.collect.Maps;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import youtube.demo.serverdiplom.Requests;

import static youtube.demo.serverdiplom.Requests.encodeParams;


public class AddressToLatLng extends AsyncTask<String, Void, ArrayList<Double>> {



    private ArrayList<Double> mas = new ArrayList<>();
    @Override
    protected ArrayList<Double> doInBackground(String... args) {
        try {
            final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
            final Map<String, String> params = Maps.newHashMap();
            params.put("sensor", "true");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
            params.put("address", "Украина, Одесса," + args[0]);// адрес, который нужно геокодировать
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
                mas.ensureCapacity(2);
                mas.add(0,location.getDouble("lng"));// долгота
                mas.add(1,location.getDouble("lat"));// широта
                System.out.println("Done");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            // как правило наиболее подходящий ответ первый и данные о координатах можно получить по пути
            // //results[0]/geometry/location/lng и //results[0]/geometry/location/lat
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mas;
    }

    @Override
    protected void onPostExecute(ArrayList<Double> doubles) {

    }
}
