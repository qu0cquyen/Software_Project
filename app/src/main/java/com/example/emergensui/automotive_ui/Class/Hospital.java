package com.example.emergensui.automotive_ui.Class;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class Hospital {

    private String name;
    private String address;
    private String hours;
    private String phone;

    private LatLng location;

    public Hospital(String name, String address, String hours, String phone, LatLng location)
    {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.phone = phone;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
