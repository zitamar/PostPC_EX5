package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    protected double [] arrayLocation = new double[3];
    private static final int REQUEST_CODE_PREMISSION_LOCATOIN = 189;
    private int isTracking = 0;
    private int homeDefined = 0;
    private String KEY_ACCURACY = "accuracy";
    private String KEY_longitude = "longitude";
    private String KEY_latitude = "latitude";







    public void track_location ()
    {
        //float accuracy = 0;
        //double latitude =0 ;
        //double longitude =0 ;
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {


                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.



                        if (location != null) {
                            // Logic to handle location object
                            Log.d("in if", "location is not null");


                            double accuracy  = location.getAccuracy();
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            arrayLocation[0] = accuracy;
                            arrayLocation[1] = latitude;
                            arrayLocation[2] = longitude;
                            Log.d("accuracy",Double.toString(accuracy));





                        }
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity activity = this;
        Log.d("fuck", "in oncreate");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        Button StartOrEndButton = (Button) findViewById(R.id.StartOrEndButton);
        final TextView startOrEndLocationTracking = (TextView) findViewById(R
                .id.startOrEndLocationTracking);
        final TextView Accuracy = (TextView) findViewById(R.id.accuracy);
        final TextView Longitude =  (TextView) findViewById(R.id.Longitude);
        final TextView Latitude = (TextView) findViewById(R.id.Latitude);
        final TextView showLatitude = (TextView) findViewById(R.id.showLatitude);
        final TextView showLongitude = (TextView) findViewById(R.id.ShowLongitude);
        final TextView showAccuracy = (TextView) findViewById(R.id.ShowAccuracy);
        final Button setHome = (Button) findViewById(R.id.setHome);
        final TextView displayHomeLoc = (TextView) findViewById(R.id.HomeLoc) ;
        final Button smsBtn = (Button) findViewById(R.id.setsms) ;
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sp.edit();

        StartOrEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasLocationPremition = ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Log.d("istracking",Integer.toString(isTracking));

                if (hasLocationPremition)
                {
                    track_location();



                    if (isTracking == 0) {
                        track_location();
                        if (homeDefined == 0)
                        {
                            if (arrayLocation[0] < 50 && arrayLocation[0] != 0.0) {
                                setHome.setVisibility(View.VISIBLE);
                                displayHomeLoc.setVisibility(View.INVISIBLE);
                            }
                        }
                        else
                        {
                            displayHomeLoc.setVisibility(View.VISIBLE);
                            setHome.setText("clear home data");
                        }

                        Accuracy.setText(Double.toString(arrayLocation[0]));
                        Latitude.setText(Double.toString(arrayLocation[1]));
                        Longitude.setText(Double.toString(arrayLocation[2]));
                        Accuracy.setVisibility(View.VISIBLE);
                        Longitude.setVisibility(View.VISIBLE);
                        Latitude.setVisibility(View.VISIBLE);
                        showAccuracy.setVisibility(View.VISIBLE);
                        showLatitude.setVisibility(View.VISIBLE);
                        showLongitude.setVisibility(View.VISIBLE);
                        startOrEndLocationTracking.setText("End tracking");

                    }
                    else if (isTracking == 1)
                    {

                        Accuracy.setVisibility(View.INVISIBLE);
                        Longitude.setVisibility(View.INVISIBLE);
                        Latitude.setVisibility(View.INVISIBLE);
                        showAccuracy.setVisibility(View.INVISIBLE);
                        showLatitude.setVisibility(View.INVISIBLE);
                        showLongitude.setVisibility(View.INVISIBLE);
                        startOrEndLocationTracking.setText("Start tracking");
                        setHome.setVisibility(View.INVISIBLE);


                    }
                    isTracking = 1-isTracking;
                }
                else
                {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission
                            .ACCESS_FINE_LOCATION},REQUEST_CODE_PREMISSION_LOCATOIN);

                }
            }

        });
        setHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHomeLoc.setVisibility(View.VISIBLE);
                setHome.setText("CLEAR HOME LOCATION");
                editor.putString(KEY_ACCURACY,Double.toString(arrayLocation[0]));
                editor.putString(KEY_latitude,Double.toString(arrayLocation[1]));
                editor.putString(KEY_longitude,Double.toString(arrayLocation[2]));
                editor.apply();
                Log.d("finish sp accuracy is", sp.getString(KEY_ACCURACY,"") );


            }
        });
        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        public void openDialog()
        {

        }


    }

}
