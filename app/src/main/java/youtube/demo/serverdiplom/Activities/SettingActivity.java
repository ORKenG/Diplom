package youtube.demo.serverdiplom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

import youtube.demo.serverdiplom.AdapterForFilters;
import youtube.demo.serverdiplom.Fragments.GmapFragment;
import youtube.demo.serverdiplom.R;

public class SettingActivity extends AppCompatActivity {


    Button accept;
    ListView filters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        accept = (Button) findViewById(R.id.acceptSettings);
        filters = (ListView) findViewById(R.id.filters);
        ArrayList<ArrayList<String>> strings = new ArrayList<>();
            ArrayList<String> line = new ArrayList<>();
            line.add(0, "filter1");
        strings.add(0, line);
            line.add(0, "filter2");
        strings.add(1, line);
            line.add(0, "filter3");
        strings.add(2, line);

        AdapterForFilters adapterForFilters = new AdapterForFilters(this, strings);
        filters.setAdapter(adapterForFilters);

    }
}
