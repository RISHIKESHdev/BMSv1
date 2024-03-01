package com.bms.bank;

public class GeoLocation {
    private int geoLocationId;
    private double latitude;
    private double longitude;

    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getGeoLocationId() {
        return geoLocationId;
    }

    public void setGeoLocationId(int geoLocationId) {
        this.geoLocationId = geoLocationId;
    }
}
