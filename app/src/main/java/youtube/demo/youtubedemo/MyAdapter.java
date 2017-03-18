package youtube.demo.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cypher on 16.03.2017.
 */

public class MyAdapter extends ArrayAdapter<ArrayList<String>> {
    private final Context context;
    private final ArrayList<ArrayList<String>> values;

    public MyAdapter(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context=context;
        this.values=objects;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.adapterName);
        TextView text = (TextView) rowView.findViewById(R.id.adapterText);
        TextView date = (TextView) rowView.findViewById(R.id.adapterDate);
       name.setText(values.get(position).get(0));
       text.setText(values.get(position).get(1));
       date.setText(values.get(position).get(2));
            return rowView;
    }
}
