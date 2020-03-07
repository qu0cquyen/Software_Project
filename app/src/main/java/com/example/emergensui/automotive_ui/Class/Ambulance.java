package com.example.emergensui.automotive_ui.Class;

public class Ambulance {
    private String heart_rate;
    private String laditude;
    private String longitude;
    private String time;
    private String vehicle_speed;

    public Ambulance(){}
    public Ambulance(String heartRate, String lad, String lon, String t, String vh)
    {
        this.heart_rate = heartRate;
        this.laditude = lad;
        this.longitude = lon;
        this.time = t;
        this.vehicle_speed = vh;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getLaditude() {
        return laditude;
    }

    public void setLaditude(String laditude) {
        this.laditude = laditude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVehicle_speed() {
        return vehicle_speed;
    }

    public void setVehicle_speed(String vehicle_speed) {
        this.vehicle_speed = vehicle_speed;
    }
}
