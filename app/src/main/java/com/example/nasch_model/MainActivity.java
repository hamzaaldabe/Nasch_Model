package com.example.nasch_model;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    private static LocationManager location;
    double speed;
    Intent service;
    static MainActivity main ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton startButton = (ImageButton) findViewById(R.id.startButton);
        ImageButton dataButton = (ImageButton) findViewById(R.id.dataButton);
        main = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            main.getSpeed();
        }

        startButton.setOnClickListener(v -> {
            service = new Intent(this, GetSpeed.class);
            startService(service);
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public double  getSpeed(){
        location = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }
        location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                speed = location.getSpeed();
                Log.e("speed " + speed," "+LocalTime.now());
            }
        });
        Toast.makeText(this,"Speed "+speed,Toast.LENGTH_SHORT).show();
        return speed;
    }

}