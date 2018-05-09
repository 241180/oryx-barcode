package com.oryx.barcode.service;

import com.oryx.barcode.activity.map.IMapViewActivity;
import com.oryx.barcode.location.AbstractLocationService;

public class GpsTrackerService extends AbstractLocationService {
    public GpsTrackerService(IMapViewActivity mapViewActivity) {
        super(mapViewActivity);
    }
}
