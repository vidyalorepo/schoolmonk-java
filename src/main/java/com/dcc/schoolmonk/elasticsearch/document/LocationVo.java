package com.dcc.schoolmonk.elasticsearch.document;

public class LocationVo {
    private double lat;
    private double lon;

    public LocationVo(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public LocationVo() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public boolean isEmpty() {
        if (this.lat == 0 && this.lon == 0)
            return true;
        return false;
    }

}
