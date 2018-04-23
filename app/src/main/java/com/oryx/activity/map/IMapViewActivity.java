package com.oryx.activity.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

public interface IMapViewActivity {
    public void onLocationChanged(Location location);
    public void onStatusChanged(String provider, int status, Bundle extras);
    public Context getApplicationContext();
}
