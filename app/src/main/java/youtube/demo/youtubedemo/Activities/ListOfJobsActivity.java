package youtube.demo.youtubedemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.youtubedemo.AsyncTasks.LoadProductsOnCurrentLatLng;
import youtube.demo.youtubedemo.MyAdapter4;
import youtube.demo.youtubedemo.R;

/**
 * Created by Cypher on 26.03.2017.
 */

public class ListOfJobsActivity extends AppCompatActivity {
    ListView jobview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_jobs);

        jobview = (ListView) findViewById(R.id.job_view);
        LoadProductsOnCurrentLatLng thread = new LoadProductsOnCurrentLatLng();
        thread.execute();
        ArrayList<ArrayList<String>> dataForAdapter;
        try {
            dataForAdapter = thread.get();
            MyAdapter4 adapter = new MyAdapter4(this, dataForAdapter);
            jobview.setAdapter(adapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


    }
}
