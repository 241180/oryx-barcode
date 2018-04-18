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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oryx.R;
import com.oryx.activity.bu.ProductDetailsActivity;
import com.oryx.activity.login.LoginActivity;
import com.oryx.activity.map.MapViewActivity;
import com.oryx.activity.settings.SettingsActivity;
import com.oryx.context.IServer;
import com.oryx.service.AuthService;
import com.oryx.utils.GuiUtils;

public abstract class AbstractActivity extends AppCompatActivity {
    private LocationManager locationManager ;
    private LocationListener listener;

    protected void initLocationComponents(){
        GuiUtils.configure_gps(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_item1) {
            return true;
        } else if (id == R.id.action_new_product) {
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            if(IServer.currentUser != null && IServer.currentUser.getId() != null) {
                AuthService.disConnect(IServer.host, IServer.currentUser.getId());
            }
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_map) {
            Intent intent = new Intent(this, MapViewActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
