package youtube.demo.youtubedemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import youtube.demo.youtubedemo.Activities.JobActivity;
import youtube.demo.youtubedemo.AsyncTasks.Delete_from_blacklist;

import static youtube.demo.youtubedemo.Fragments.GmapFragment.current_id;
import static youtube.demo.youtubedemo.Fragments.GmapFragment.current_user_id;

/**
 * Created by Cypher on 16.03.2017.
 */

public class MyAdapter4 extends ArrayAdapter<ArrayList<String>> {
    private final Context context;
    private final ArrayList<ArrayList<String>> values;
    private String id;

    public MyAdapter4(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context = context;
        this.values = objects;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter4, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.job_title);
        TextView price = (TextView) rowView.findViewById(R.id.job_price);
        name.setText(values.get(position).get(0));
        price.setText(values.get(position).get(7));
        id = values.get(position).get(5);
        final String phone = values.get(position).get(3);
        final String address = values.get(position).get(8);
        final String nameu = values.get(position).get(0);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bundle = new Intent(getContext(), JobActivity.class);
                bundle.putExtra("phone", phone);
                bundle.putExtra("address", address);
                bundle.putExtra("name", nameu);
                current_id = values.get(position).get(5);
                current_user_id = values.get(position).get(6);
                getContext().startActivity(bundle);
            }
        });
        return rowView;

    }
}
