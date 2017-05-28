package youtube.demo.serverdiplom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import youtube.demo.serverdiplom.AsyncTasks.Delete_from_blacklist;

import static youtube.demo.serverdiplom.Fragments.GmapFragment.marker_type;

/**
 * Created by cypher on 20.05.17.
 */

public class AdapterForFilters  extends ArrayAdapter<ArrayList<String>>  {

    private final Context context;
    private final ArrayList<ArrayList<String>> values;

    public AdapterForFilters(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context=context;
        this.values = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.adapter_filters, parent, false);
        CheckBox filter = (CheckBox) rowView.findViewById(R.id.filter1);
        TextView text = (TextView) rowView.findViewById(R.id.filter);
        text.setText(values.get(0).get(position));
        filter.setChecked(marker_type[position] != null);
        filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    marker_type[position] = position+1;
                } else {
                    marker_type[position] = null;
                }
            }
        });
        return rowView;

    }

}
