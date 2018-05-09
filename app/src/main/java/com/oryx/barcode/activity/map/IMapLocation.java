package com.oryx.barcode.activity.map;

import android.location.Location;

public class IMapLocation extends Location {
    private String uniqueId;
    private String title;
    private String description;

    public IMapLocation(String provider) {
        super(provider);
    }

    public IMapLocation(Location l) {
        super(l);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueId() {
        if (uniqueId == null) {
            return title;
        }
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
