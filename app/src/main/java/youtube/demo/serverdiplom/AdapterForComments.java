package youtube.demo.serverdiplom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import youtube.demo.serverdiplom.Activities.MyUser_Profile;
import youtube.demo.serverdiplom.Activities.User_Profile;
import youtube.demo.serverdiplom.AsyncTasks.LoadUserProfile;

import static youtube.demo.serverdiplom.Activities.MainActivity.flagForMyProfile;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.current_user_id;
import static youtube.demo.serverdiplom.Fragments.GmapFragment.myId;

/**
 * Created by Cypher on 16.03.2017.
 */

public class AdapterForComments extends ArrayAdapter<ArrayList<String>> {
    private final Context context;
    private final ArrayList<ArrayList<String>> values;

    public AdapterForComments(Context context, ArrayList<ArrayList<String>> objects) {
        super(context, R.layout.adapter, objects);
        this.context=context;
        this.values=objects;
    }



    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.adapterName);
        TextView text = (TextView) rowView.findViewById(R.id.adapterText);
        TextView date = (TextView) rowView.findViewById(R.id.adapterDate);
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);
        Picasso.with(getContext()).load("http://7kmcosmetics.com/" + values.get(position).get(4)).into(photo);
       name.setText(values.get(position).get(0));
       text.setText(values.get(position).get(1));
       date.setText(values.get(position).get(2));

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!values.get(position).get(3).equals(myId)) {
                    flagForMyProfile = false;
                    current_user_id = values.get(position).get(3);
                    LoadUserProfile l = new LoadUserProfile();
                    l.execute();
                    ArrayList<ArrayList<String>> counts = new ArrayList<>();
                    counts.ensureCapacity(4);
                    try {
                        counts = l.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getContext(), User_Profile.class);
                    intent.putExtra("count", counts.get(0).get(0));
                    intent.putExtra("name", counts.get(0).get(1));
                    intent.putExtra("surname", counts.get(0).get(2));
                    intent.putExtra("avg", counts.get(1).get(0));
                    ArrayList<ArrayList<String>> counts2 = new ArrayList<>();
                    ArrayList<String> id = new ArrayList<>();
                    counts2.ensureCapacity(5);
                    for (int i = 2; i < counts.size(); i++) {
                        ArrayList<String> line = new ArrayList<>();
                        line.add(0, counts.get(i).get(1));
                        line.add(1, counts.get(i).get(0));
                        line.add(2, counts.get(i).get(3));
                        line.add(3, counts.get(i).get(4));
                        line.add(4, counts.get(i).get(2));
                        line.add(5, counts.get(i).get(5));
                        counts2.add(i - 2, line);
                        id.add(i - 2, counts.get(i).get(2));
                    }
                    intent.putExtra("review", counts2);
                    intent.putExtra("id", id);
                    getContext().startActivity(intent);
                } else {
                    flagForMyProfile = true;
                    Intent intent = new Intent(getContext(), MyUser_Profile.class);
                    LoadUserProfile l = new LoadUserProfile();
                    l.execute();
                    ArrayList<ArrayList<String>> counts = new ArrayList<>();
                    counts.ensureCapacity(9);
                    try {
                        counts = l.get();
                        ArrayList<ArrayList<String>> counts2 = new ArrayList<>();
                        ArrayList<String> idd = new ArrayList<>();
                        counts2.ensureCapacity(9);
                        for (int i = 2; i < counts.size(); i++) {
                            ArrayList<String> line = new ArrayList<>();
                            line.add(0, counts.get(i).get(1));
                            line.add(1, counts.get(i).get(0));
                            line.add(2, counts.get(i).get(3));
                            line.add(3, counts.get(i).get(4));
                            line.add(4, counts.get(i).get(2));
                            line.add(5, counts.get(i).get(5));
                            counts2.add(i - 2, line);
                            idd.add(i - 2, counts.get(i).get(2));
                        }
                        intent.putExtra("review", counts2);
                        intent.putExtra("id", idd);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }

                    intent.putExtra("count", counts.get(0).get(0));
                    intent.putExtra("name", counts.get(0).get(1));
                    intent.putExtra("surname", counts.get(0).get(2));
                    intent.putExtra("secondname", counts.get(0).get(3));
                    intent.putExtra("phone", counts.get(0).get(4));
                    intent.putExtra("mail", counts.get(0).get(5));
                    intent.putExtra("photo", counts.get(0).get(6));
                    intent.putExtra("avg", counts.get(1).get(0));
                    getContext().startActivity(intent);
                }
            }
        });

            return rowView;

    }
}
