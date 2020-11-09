package com.oryx.barcode.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.oryx.barcode.activity.map.IMapViewActivity;


public abstract class AbstractLocationService extends Service implements ILocationService {
    private static final String TAG = "AbstractLocationService";
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 100f;
    private LocationManager mLocationManager = null;
    private IMapViewActivity mapViewActivity;
    private LocationListener[] mLocationListeners;

    public AbstractLocationService(IMapViewActivity mapViewActivity) {
        this.mapViewActivity = mapViewActivity;

        mLocationListeners = new LocationListener[]{
                new LocationListener(LocationManager.GPS_PROVIDER)
                //, new LocationListener(LocationManager.NETWORK_PROVIDER)
        };
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        Log.e(TAG, "onCreate");

        initializeLocationManager();

        boolean gpsProviderFail = false;
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
            gpsProviderFail = true;
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist, " + ex.getMessage());
            gpsProviderFail = true;
        }

        if (gpsProviderFail) {
            try {
                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        LOCATION_INTERVAL,
                        LOCATION_DISTANCE,
                        mLocationListeners[1]
                );
            } catch (java.lang.SecurityException ex) {
                Log.i(TAG, "fail to request location update, ignore", ex);
            } catch (IllegalArgumentException ex) {
                Log.d(TAG, "network provider does not exist " + ex.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listener, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: " + LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null && this.mapViewActivity != null) {
            mLocationManager = (LocationManager) this.mapViewActivity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public void onLocationChanged(Location location) {
        if (mapViewActivity != null) {
            mapViewActivity.onLocationChanged(location);
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (mapViewActivity != null) {
            mapViewActivity.onStatusChanged(provider, status, extras);
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            AbstractLocationService.this.onLocationChanged(mLastLocation);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            AbstractLocationService.this.onLocationChanged(mLastLocation);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
            AbstractLocationService.this.onStatusChanged(provider, status, extras);
        }
    }
}
