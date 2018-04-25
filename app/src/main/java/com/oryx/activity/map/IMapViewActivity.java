package com.oryx.activity.map;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IMapViewActivity {
    public void onLocationChanged(Location location);

    public void onStatusChanged(String provider, int status, Bundle extras);

    public Context getApplicationContext();

    public LatLng setLocation(IMapLocation location);

    public void setLocations(List<IMapLocation> locations);

    public void removeLocation(String uniqueId);

    public void removeAllLocations();

    public void zoomOn(LatLng latLng, float zoom);
}
