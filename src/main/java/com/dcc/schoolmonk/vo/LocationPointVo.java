package com.dcc.schoolmonk.vo;

public class LocationPointVo {
    private float lat;
    private float lon;

    public LocationPointVo() {
    }

    public LocationPointVo(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "LocationPointVo [lat=" + lat + ", lon=" + lon + "]";
    }

}
