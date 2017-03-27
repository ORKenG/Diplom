package youtube.demo.youtubedemo.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import youtube.demo.youtubedemo.Fragments.GmapFragment;
import youtube.demo.youtubedemo.AsyncTasks.AddressToLatLng;
import youtube.demo.youtubedemo.AsyncTasks.PlaceAutocomplete;
import youtube.demo.youtubedemo.R;

public class SetMarkerActivity extends AppCompatActivity {

    EditText editText;
    AutoCompleteTextView address_autocompleteTextView;
    Button btn_addByAddress;
    Button btn_addByClick;
    Button btn_addOnMyPosition;
    String[] data_for_spinner = {"first type", "second type", "third type"};
    Spinner spinner;
    View setMarkerView;
    View setMarker_progress;
    String[] values = new String[2];
    ArrayList<String> lst;
    EditText enter_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        values[0]="test";
        values[1]="test2";
        lst = new ArrayList<>(Arrays.asList(values));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_marker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = (EditText) findViewById(R.id.set_text);
        address_autocompleteTextView = (AutoCompleteTextView) findViewById(R.id.enter_street);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_for_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enter_price = (EditText) findViewById(R.id.enter_price);
        spinner = (Spinner) findViewById(R.id.spinner);
        setMarkerView =  findViewById(R.id.setMarkerView);
        setMarker_progress = findViewById(R.id.setMarker_progress);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, lst);
        address_autocompleteTextView.setAdapter(adapter2);
        address_autocompleteTextView.setThreshold(1);
        address_autocompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence constraint = address_autocompleteTextView.getText();
                adapter2.getFilter().filter(constraint);
                    address_autocompleteTextView.showDropDown();
            }
        });
     address_autocompleteTextView.setOnKeyListener(new View.OnKeyListener() {
         @Override
         public boolean onKey(View v, int keyCode, KeyEvent event) {
             PlaceAutocomplete pl = new PlaceAutocomplete();
             pl.execute(address_autocompleteTextView.getText().toString());
             try {

                 String[] g = pl.get();
                 System.out.println("g= " + g);
                 if (g!=null){
                     adapter2.clear();
                     for (int i = 0; i <4 ; i++) {
                         if (g[i]!=null)
                         adapter2.insert(g[i], 0);
                     }

                 adapter2.notifyDataSetChanged();}
             } catch (InterruptedException | ExecutionException e) {
                 e.printStackTrace();
             }
             return false;
         }
     });
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + spinner.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btn_addByAddress = (Button) findViewById(R.id.btn_addByAddress);
        btn_addByAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(1);
            }
        });
        btn_addByClick = (Button) findViewById(R.id.btn_addByClick);
        btn_addByClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(2);
            }
        });
        btn_addOnMyPosition = (Button) findViewById(R.id.btn_addOnMyPosition);
        btn_addOnMyPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input(3);
            }
        });
    }
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 400;

            setMarkerView.setVisibility(show ? View.GONE : View.VISIBLE);
            setMarkerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setMarkerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            setMarker_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            setMarker_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setMarker_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            setMarker_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            setMarkerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }


    }
    public void input(int type) {
        showProgress(true);
        int x = spinner.getSelectedItemPosition()+1;
        if (editText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Введите все данные", Toast.LENGTH_LONG).show();
            showProgress(false);
            return;

        }
        Intent intent = new Intent();
        intent.putExtra("name", editText.getText().toString());
        intent.putExtra("type", String.valueOf(x));
        intent.putExtra("phone", GmapFragment.userPhone);
        intent.putExtra("price", enter_price.getText().toString());
        if (type == 1){

            String ms[] = new String[1];
            ms[0] = address_autocompleteTextView.getText().toString();

            intent.putExtra("address",address_autocompleteTextView.getText().toString());
            AddressToLatLng thread = new AddressToLatLng();
            thread.execute(ms);
            try {
                ArrayList<Double> abc = thread.get();
                intent.putExtra("lat", abc.get(1).toString());
                intent.putExtra("lng", abc.get(0).toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

        intent.putExtra("typeOfMessage",type);
        setResult(RESULT_OK, intent);
        finish();
    }

}
