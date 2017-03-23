package youtube.demo.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import youtube.demo.youtubedemo.AsyncTasks.Delete_from_blacklist;

/**
 * Created by Cypher on 16.03.2017.
 */

public class MyAdapter3 extends ArrayAdapter<ArrayList<String>> {
    private final Context context;
    private final ArrayList<ArrayList<String>> values;
    private String id;

    public MyAdapter3(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context = context;
        this.values = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.adapter3, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.adapterName);
        final FloatingActionButton delete = (FloatingActionButton) rowView.findViewById(R.id.removeFromBlacklist);
        name.setText(values.get(position).get(0) + " " + values.get(position).get(1));
        id = values.get(position).get(2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_from_blacklist delete_from_blacklist = new Delete_from_blacklist();
                delete_from_blacklist.execute(id);
                rowView.setVisibility(View.GONE);

            }
        });
        return rowView;

    }
}
