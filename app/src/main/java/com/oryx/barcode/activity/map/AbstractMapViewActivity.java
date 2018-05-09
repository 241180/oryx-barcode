package com.oryx.barcode.activity.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.oryx.barcode.R;
import com.oryx.barcode.activity.core.ActionBarActivity;
import com.oryx.barcode.context.IServer;
import com.oryx.barcode.service.GpsTrackerService;
import com.oryx.barcode.utils.GuiUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbstractMapViewActivity extends ActionBarActivity implements IMapViewActivity {
    public static String MY_LOCATION = "MY_LOCATION";
    @BindView(R.id.mapView)
    MapView mMapView;
    private GoogleMap googleMap;
    private Map<String, Marker> markerMap = new HashMap<>();
    private GpsTrackerService gpsTrackerService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        ButterKnife.bind(this);

        initMapView(savedInstanceState, mMapView);
    }

    public void initMapView(Bundle savedInstanceState, MapView mMapView) {
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(this.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(AbstractMapViewActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(AbstractMapViewActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            GuiUtils.configure_gps(AbstractMapViewActivity.this);
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                Location location = new Location(LocationManager.GPS_PROVIDER);
                location.setLatitude(-34);
                location.setLongitude(151);
                if (IServer.location == null) {
                    IServer.location = new IMapLocation(location);
                } else {
                    IServer.location.set(location);
                }
                IServer.location.setTitle(MY_LOCATION);
                IServer.location.setDescription(Calendar.getInstance().getTime().toString());
                zoomOn(setLocation(IServer.location), 12f);
                gpsTrackerService = new GpsTrackerService(AbstractMapViewActivity.this);
                gpsTrackerService.onCreate();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        gpsTrackerService.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gpsTrackerService.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (IServer.location == null) {
            IServer.location = new IMapLocation(location);
        } else {
            IServer.location.set(location);
        }
        IServer.location.setTitle(MY_LOCATION);
        IServer.location.setDescription(Calendar.getInstance().getTime().toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public LatLng setLocation(IMapLocation location) {
        // For dropping a marker at a point on the Map
        LatLng latLng = new LatLng(IServer.location.getLatitude(), IServer.location.getLongitude());
        Marker marker = addMarker(latLng, location.getTitle(), location.getDescription());
        markerMap.put(location.getUniqueId(), marker);
        return latLng;
    }

    @Override
    public void setLocations(List<IMapLocation> locations) {
        for (IMapLocation mapLocation : locations) {
            setLocation(mapLocation);
        }
    }

    @Override
    public void removeLocation(String uniqueId) {
        if (markerMap.containsKey(uniqueId)) {
            markerMap.remove(uniqueId).remove();
        }
    }

    @Override
    public void removeAllLocations() {
        for (String uniqueId : markerMap.keySet()) {
            markerMap.remove(uniqueId).remove();
        }
    }

    @Override
    public void zoomOn(LatLng latLng, float zoom) {
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(zoom).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        ;
    }

    private Marker addMarker(LatLng latLng, String title, String description) {
        return googleMap.addMarker(new MarkerOptions().position(latLng).title(title).snippet(description));
    }
}