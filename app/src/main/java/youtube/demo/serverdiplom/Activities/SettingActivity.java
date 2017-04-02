package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.R;

public class SettingActivity extends AppCompatActivity {

    CheckBox filter1;
    CheckBox filter2;
    CheckBox filter3;
    Button accept;
    Button Geo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        filter1 = (CheckBox) findViewById(R.id.filter1);
        filter2 = (CheckBox) findViewById(R.id.filter2);
        filter3 = (CheckBox) findViewById(R.id.filter3);
        accept = (Button) findViewById(R.id.acceptSettings);
        Geo = (Button) findViewById(R.id.Geo);
        filter1.setChecked(GmapFragment.marker_type[0] != null);
        filter2.setChecked(GmapFragment.marker_type[1] != null);
        filter3.setChecked(GmapFragment.marker_type[2] != null);
        Geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        filter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter1.isChecked()){
                    GmapFragment.marker_type[0] = 1;
                }
                else {
                    GmapFragment.marker_type[0] = null;
                }
            }
        });
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter2.isChecked()){
                    GmapFragment.marker_type[1] = 2;
                }
                else {
                    GmapFragment.marker_type[1] = null;
                }
            }
        });
        filter3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter3.isChecked()){
                    GmapFragment.marker_type[2] = 3;
                }
                else {
                    GmapFragment.marker_type[2] = null;
                }
            }
        });

    }
}
