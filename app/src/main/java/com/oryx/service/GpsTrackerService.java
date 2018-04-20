package com.oryx.service;

import com.oryx.activity.map.IMapViewActivity;
import com.oryx.location.AbstractLocationService;

public class GpsTrackerService extends AbstractLocationService{
    public GpsTrackerService(IMapViewActivity mapViewActivity) {
        super(mapViewActivity);
    }
}
