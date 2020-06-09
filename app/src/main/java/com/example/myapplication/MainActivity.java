package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
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
        StartOrEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasLocationPremition = ActivityCompat.checkSelfPermission(activity,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                Log.d("istracking",Integer.toString(isTracking));

                if (hasLocationPremition)
                {
                    if (isTracking == 0) {
                        track_location();

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
                        startOrEndLocationTracking.setText("End tracking");

                    }
                    isTracking = 1-isTracking;
                }
                else
                {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_PREMISSION_LOCATOIN);

                }
            }

        });
    }

}
