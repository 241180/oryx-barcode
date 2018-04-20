package com.oryx.location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.oryx.activity.map.IMapViewActivity;


public interface ILocationService {
    public void onLocationChanged(Location location);
    public void onStatusChanged(String provider, int status, Bundle extras);
}
