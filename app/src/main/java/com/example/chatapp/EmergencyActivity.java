package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EmergencyActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    EmergencyContactDatabase emergencyContactDatabase = new EmergencyContactDatabase(this);
    ArrayList<String> pinCodes = new ArrayList<>();
    ArrayList<String> latitudes = new ArrayList<>();
    ArrayList<String> longitudes = new ArrayList<>();
    ArrayList<Float> distances = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        Cursor cursor = emergencyContactDatabase.getAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Contacts", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }else{
            while(cursor.moveToNext()){
                pinCodes.add(cursor.getString(3));
            }
        }
        convert();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLocation();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getLocation();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
            }
        }else {
            Toast.makeText(this, "Please Give Permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    public void convert(){
        Geocoder geocoder = new Geocoder(this);
        try{
            for(int i = 0;i<pinCodes.size();i++){
                List<Address> addresses = geocoder.getFromLocationName(pinCodes.get(i),1);
                Address address = addresses.get(0);
                latitudes.add(Double.toString(address.getLatitude()));
                longitudes.add(Double.toString(address.getLongitude()));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getLocation(){
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Location location1 = new Location("User");
                location1.setLatitude(location.getLatitude());
                location1.setLongitude(location.getLongitude());
                Location location2 = new Location("Emergency");
                for(int i = 0;i<latitudes.size();i++){
                    location2.setLongitude(Double.parseDouble(longitudes.get(i)));
                    location2.setLatitude(Double.parseDouble(latitudes.get(i)));
                    distances.add(location1.distanceTo(location2));
                }
                int min = distances.indexOf(Collections.min(distances));
                //Log.i("NOTHING",pinCodes.get(min));
                Cursor cursor = emergencyContactDatabase.getCallingNumber(pinCodes.get(min));
                cursor.moveToNext();
                if(cursor.getCount() == 0)
                    Toast.makeText(EmergencyActivity.this, "Invalid Pin", Toast.LENGTH_SHORT).show();
                else{
                    String number = cursor.getString(2);
                    Log.i("NUMBER: ",number);
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+number)));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ContextCompat.checkSelfPermission(EmergencyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(EmergencyActivity.this, "Enable GPS", Toast.LENGTH_SHORT).show();
            }
        };
    }
    }

