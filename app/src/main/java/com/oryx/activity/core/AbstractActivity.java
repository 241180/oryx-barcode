package com.oryx.activity.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.oryx.utils.GuiUtils;

public abstract class AbstractActivity extends AppCompatActivity {
    private LocationManager locationManager ;
    private LocationListener listener;

    protected void initComponenet(){
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        this.listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("asd", "---------------- GPS : " + location.getLongitude() + " / " + location.getLatitude());
                AbstractActivity.this.onLocationChange(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

    }
    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    protected void savePreferences(){

    }
    protected void loadPreferences(){

    }

    protected void onLocationChange(Location location){

    }

    @SuppressLint("MissingPermission")
    public void requestLocationUpdates() {
        GuiUtils.showWorker(
                new Runnable() {
                    public void run() {
                    }
                }, 3000);

        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
    }
}
