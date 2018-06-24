package com.akin.mapu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MAINACTIVITY","Inside OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        button = (Button)findViewById(R.id.button);

boolean b=db.isDataLoaded();
if(b==false) {
    db.insertquery("100D", "R.K.Hospital", 13.013782, 77.635559);
    db.insertquery("500D", "Reliance Fresh", 13.013066, 77.630975);
    db.insertquery("500D", "Device Bar", 13.012865, 77.635227);
    db.insertquery("100D", "Empire", 13.014630, 77.635565);

}

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        final String[] spinnerdataarray = db.fetchquery();
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, spinnerdataarray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Log.i("MAINACTIVITY","Inside OnItemSelected");
                String value = spinner.getSelectedItem().toString();
                populatesecondspinner(value);

                Log.i("MAINACTIVITY","Leaving OnItemSelected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busnumber = spinner.getSelectedItem().toString();
                String stop =spinner1.getSelectedItem().toString();

                double longitude,latitude;
                latitude=db.getlatitude(busnumber,stop);
                longitude=db.getlongitude(busnumber,stop);

                Toast.makeText(getBaseContext(),
                        "" + latitude +" , "+longitude,
                        Toast.LENGTH_SHORT).show();

                Intent intent= new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("LATITUDE",latitude);
                intent.putExtra("LONGITUDE",longitude);
                startActivity(intent);
            }});

    }
    private void populatesecondspinner(String busnumber){
        Log.i("MAINACTIVITY","Inside populate second spinner");
        String[] spinnerdataarraystop = db.fetchquery1(busnumber);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, spinnerdataarraystop);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(spinnerAdapter1);
        Log.i("MAINACTIVITY","leaving populatesecondlistener");
    }

}
