package com.oryx.barcode.activity.map;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.oryx.barcode.context.StaticServer;

public class TruckerViewActivity extends AbstractMapViewActivity implements IMapViewActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        zoomOn(setLocation(StaticServer.location), 12f);
    }

}