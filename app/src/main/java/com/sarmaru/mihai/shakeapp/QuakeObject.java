package com.sarmaru.mihai.shakeapp;

/**
 * Created by Mihai Sarmaru on 13.12.2014.
 */
public class QuakeObject {

    private int _id;
    private double _latitude;
    private double _longitude;
    private int _depth;
    private double _magnitude;
    private long _time;
    private String _region;

    public QuakeObject() {}

    public QuakeObject(int id, double latitude, double longitude,
                       int depth, double magnitude, long time, String region) {
        this._id = id;
        this._latitude = latitude;
        this._longitude = longitude;
        this._depth = depth;
        this._magnitude = magnitude;
        this._region = region;

        setTime(time);
        this._time = getTime();
    }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        this._id = id;
    }

    public double getLatitude() {
        return _latitude;
    }
    public void setLatitude(double latitude) {
        this._latitude = latitude;
    }

    public double getLongitude() {
        return _longitude;
    }
    public void setLongitude(double longitude) {
        this._longitude = longitude;
    }

    public int getDepth() {
        return _depth;
    }
    public void setDepth(int depth) {
        this._depth = depth;
    }

    public double getMagnitude() {
        return _magnitude;
    }
    public void setMagnitude(double magnitude) {
        this._magnitude = magnitude;
    }

    public long getTime() {
        return _time;
    }
    public void setTime(long time) {
        this._time = time * 1000;
    }

    public String getRegion() {
        return _region;
    }
    public void setRegion(String region) {
        this._region = region;
    }
}
