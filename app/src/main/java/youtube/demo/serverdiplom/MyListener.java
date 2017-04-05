package youtube.demo.serverdiplom;

import android.view.View;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.AsyncTasks.CreateNewProduct;
import youtube.demo.serverdiplom.AsyncTasks.LatLngToLatLng;
import youtube.demo.serverdiplom.Fragments.GmapFragment;

/**
 * Created by Cypher on 25.03.2017.
 */

public class MyListener implements View.OnClickListener {
    private Marker marker;
    private String type;
    private String name;
    private String phone;
    private String price;
    private GmapFragment gmapFragment;
    public MyListener(Marker marker, String type, String name, String phone, GmapFragment gmapFragment, String price){
        this.marker = marker;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.gmapFragment = gmapFragment;
        this.price=price;
    }
    @Override
    public void onClick(View v) {
        String ms[] = new String[2];
        String args[] = new String [9];
        ms[0] = Double.toString(marker.getPosition().latitude);
        ms[1] = Double.toString(marker.getPosition().longitude);
        LatLngToLatLng thread = new LatLngToLatLng();
        thread.execute(ms);
        try {
            ArrayList<String> abc = thread.get();
            args[0] = name;
            args[1] = abc.get(1);
            args[2] = abc.get(0);
            args[3] = type;
            args[4] = phone;
            args[5] =  ms[0];
            args[6] =  ms[1];
            args[7] =  abc.get(2);
            args[8] =  price;
            CreateNewProduct createNewProduct = new CreateNewProduct();
            createNewProduct.execute(args);
            gmapFragment.dbRead();
            gmapFragment.hideAndShow.setVisibility(View.INVISIBLE);
            gmapFragment.confirm.setVisibility(View.INVISIBLE);
            marker.remove();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
