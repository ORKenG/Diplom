package youtube.demo.serverdiplom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import youtube.demo.serverdiplom.AsyncTasks.Delete_from_blacklist;

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
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);
        String forImage = values.get(position).get(3);
        byte[] decodedString = Base64.decode(forImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        photo.setImageBitmap(decodedByte);
        final Button delete = (Button) rowView.findViewById(R.id.removeFromBlacklist);
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
