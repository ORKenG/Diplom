package youtube.demo.youtubedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Cypher on 16.03.2017.
 */

public class MyAdapter2 extends ArrayAdapter<ArrayList<String>> {
    private final Context context;
    private final ArrayList<ArrayList<String>> values;

    public MyAdapter2(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context=context;
        this.values=objects;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter2, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.adapterName);
        TextView text = (TextView) rowView.findViewById(R.id.adapterText);
        TextView date = (TextView) rowView.findViewById(R.id.adapterDate);
        ImageView starOne = (ImageView) rowView.findViewById(R.id.starOne);
        ImageView starTwo = (ImageView) rowView.findViewById(R.id.starTwo);
        ImageView starThree = (ImageView) rowView.findViewById(R.id.starThree);
        ImageView starFour = (ImageView) rowView.findViewById(R.id.starFour);
        ImageView starFive = (ImageView) rowView.findViewById(R.id.starFive);
        ImageView[] a = {starOne,starTwo,starThree,starFour,starFive};
        for (int i = 0; i <Integer.parseInt(values.get(position).get(3)); i++) {
            a[i].setVisibility(View.VISIBLE);
        }
        name.setText(values.get(position).get(0));
        text.setText(values.get(position).get(1));
        date.setText(values.get(position).get(2));
        return rowView;
    }
}
