package youtube.demo.youtubedemo.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import youtube.demo.youtubedemo.Activities.JobActivity;
import youtube.demo.youtubedemo.Activities.ListOfJobsActivity;
import youtube.demo.youtubedemo.Activities.SetMarkerActivity;
import youtube.demo.youtubedemo.AsyncTasks.CreateNewProduct;
import youtube.demo.youtubedemo.AsyncTasks.LatLngToLatLng;
import youtube.demo.youtubedemo.AsyncTasks.LoadAllProducts;
import youtube.demo.youtubedemo.MyListener;
import youtube.demo.youtubedemo.R;

import static youtube.demo.youtubedemo.Activities.MainActivity.flag;

public class GmapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    public static Integer marker_type[] = {1, 2, 3};
    FloatingActionButton button;
    Geocoder geocoder;
    Marker myPosition;
    String args[] = new String[9];
    private LocationManager locationManager;
    public static double myLng = 0;
    public static double myLat = 0;
    public static String current_id;
    public static String current_user_id;
    public static String current_Lat;
    public static String current_Lng;
    public static String userPhone;
    private Map<Marker, String> allMarkersMap = new HashMap<>();
    private Map<String, Marker> MarkersMap = new HashMap<>();
    private Map<Marker, String> allUserMap = new HashMap<>();
    private Map<Marker, String> markerLatMap = new HashMap<>();
    private Map<Marker, String> markerLngMap = new HashMap<>();
    public Button hideAndShow;
    public Button confirm;
    private EditText search_EditText;
    ArrayList<ArrayList<String>> arrayLists;
    ImageButton searchBtn;
    FloatingActionButton moveCamera;
    private boolean flagForVisible=false;
    public static boolean flagForClick=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gmaps, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data == null) {
            return;
        }

        final String type = data.getStringExtra("type");
        System.out.println(type);
        String name = data.getStringExtra("name");
        String phone = data.getStringExtra("phone");
        String price = data.getStringExtra("price");
        int add_type = data.getIntExtra("typeOfMessage", 3);
        switch (add_type) {
            case 1:
                args[0] = name;
                args[1] = data.getStringExtra("lat");
                args[2] = data.getStringExtra("lng");
                args[3] = type;
                args[4] = phone;
                args[5] = data.getStringExtra("lat");
                args[6] = data.getStringExtra("lng");
                args[7] = data.getStringExtra("address");
                args[8] = price;
                CreateNewProduct createNewProduct = new CreateNewProduct();
                createNewProduct.execute(args);
                dbRead();


                break;
            case 2:
                Marker marker = mMap.addMarker(new MarkerOptions().
                        position(new LatLng(myLat, myLng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        marker.setDraggable(true);

                hideAndShow.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);

                 name = data.getStringExtra("name");
                 phone = data.getStringExtra("phone");
                MyListener myListener = new MyListener(marker, type, name, phone, this, price);
              confirm.setOnClickListener(myListener);
                mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker marker) {

                        }

                        @Override
                        public void onMarkerDrag(Marker marker) {

                        }

                        @Override
                        public void onMarkerDragEnd(Marker marker) {

                        }
                    });

                break;
            case 3:
                String ms[] = new String[2];
                ms[0] = Double.toString(myLat);
                ms[1] = Double.toString(myLng);


                LatLngToLatLng thread = new LatLngToLatLng();
                thread.execute(ms);
                try {
                    ArrayList<String> abc = thread.get();
                     name = data.getStringExtra("name");
                    phone = data.getStringExtra("phone");
                    args[0] = name;
                    args[1] = abc.get(1);
                    args[2] = abc.get(0);
                    args[3] = type;
                    args[4] = phone;
                    args[5] =  ms[0];
                    args[6] =  ms[1];
                    args[7] =  abc.get(2);
                    args[8] =  price;
                     createNewProduct = new CreateNewProduct();
                    createNewProduct.execute(args);
                    dbRead();
                    hideAndShow.setVisibility(View.INVISIBLE);
                    confirm.setVisibility(View.INVISIBLE);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        JobActivity.textForPhone = "";
        JobActivity.textForTitle = "";
        JobActivity.textForAddress = "";
        MapFragment fragment = new MapFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.dummy, fragment).commit();
        hideAndShow = (Button) getActivity().findViewById(R.id.hideAndShow);
        hideAndShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAndshow();
            }
        });
        searchBtn = (ImageButton) getActivity().findViewById(R.id.button_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        confirm = (Button) getActivity().findViewById(R.id.confirm);

        moveCamera = (FloatingActionButton) getActivity().findViewById(R.id.button_move_camera);
        moveCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCamera();
            }
        });
        button = (FloatingActionButton) getActivity().findViewById(R.id.button_set_marker);
        search_EditText = (EditText) getActivity().findViewById(R.id.search_EditText);
        ViewGroup layout = (ViewGroup) button.getParent();
        if (!flag) {
            layout.removeView(button);

        } else {
            layout.removeView(searchBtn);
            layout.removeView(search_EditText);
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        fragment.getMapAsync(this);

    }

    public void add() {
        Intent intent = new Intent(getActivity(), SetMarkerActivity.class);
        startActivityForResult(intent, 1);
    }

    private void search() {
        Marker marker;
        for (int i = 0; i < allMarkersMap.size(); i++) {
            if (!arrayLists.get(i).get(0).contains(search_EditText.getText().toString())) {
                marker = MarkersMap.get(arrayLists.get(i).get(0));
                marker.setVisible(false);
            } else {
                marker = MarkersMap.get(arrayLists.get(i).get(0));
                marker.setVisible(true);
            }

        }
    }

    private void showMe(Location location) {
        if (myPosition != null) {
            myPosition.remove();
        }
        myLat = location.getLatitude();
        myLng = location.getLongitude();
        myPosition = mMap.addMarker(new MarkerOptions().position(new LatLng(myLat, myLng))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name)));
    }

    private void moveCamera() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLng), 13));
    }

    private void showMe() {
        if (myPosition != null) {
            myPosition.remove();
        }
        if (myLat != 0 && myLng != 0) {
            myPosition = mMap.addMarker(new MarkerOptions().position(new LatLng(myLat, myLng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLat, myLng), 13));
        }
    }

    private android.location.LocationListener locationListener = new android.location.LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showMe(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public void openJob() {
        Intent bundle = new Intent(getActivity(), ListOfJobsActivity.class);
        startActivity(bundle);
    }
    public void hideAndshow(){
        Marker marker;
        for (int i = 0; i <allMarkersMap.size() ; i++) {
            marker = MarkersMap.get(arrayLists.get(i).get(0));
            marker.setVisible(flagForVisible);
        }
        flagForVisible=!flagForVisible;
    }
    public void dbRead() {
        System.out.println(marker_type[0]);
        LoadAllProducts l = new LoadAllProducts();
        l.execute();
        try {
            arrayLists = l.get();
            for (int i = 0; i < arrayLists.size(); i++) {
                    Marker marker = mMap.addMarker(new MarkerOptions().
                            position(new LatLng(Double.parseDouble(arrayLists.get(i).get(1)), Double.parseDouble(arrayLists.get(i).get(2))))
                            .title("Доступно работ: " + arrayLists.get(i).get(0))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .snippet(arrayLists.get(i).get(3)));
                        markerLatMap.put(marker,arrayLists.get(i).get(1));
                        markerLngMap.put(marker,arrayLists.get(i).get(2));
                   /* allMarkersMap.put(marker, arrayLists.get(i).get(5));
                    allUserMap.put(marker, arrayLists.get(i).get(6));
                    MarkersMap.put(arrayLists.get(i).get(0), marker);*/
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            current_Lat=markerLatMap.get(marker);
                            current_Lng=markerLngMap.get(marker);
                            return false;
                        }
                    });

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                openJob();
            }
        });

        dbRead();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                100, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 100, 10,
                locationListener);
        showMe();


    }
}
