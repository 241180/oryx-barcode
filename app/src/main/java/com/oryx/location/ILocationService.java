package com.oryx.location;

import android.location.Location;
import android.os.Bundle;


public interface ILocationService {
    public void onLocationChanged(Location location);

    public void onStatusChanged(String provider, int status, Bundle extras);
}
