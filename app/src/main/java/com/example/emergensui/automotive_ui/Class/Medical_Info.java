package com.example.emergensui.automotive_ui.Class;

public class Medical_Info {

    private String date;
    private int blood_pressure;
    private int heart_rate;
    private int oxygen;

    public Medical_Info(){}

    public Medical_Info(String date, int bs, int hr, int oxy)
    {
        this.date = date;
        this.blood_pressure = bs;
        this.heart_rate = hr;
        this.oxygen = oxy;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public int getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(int blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }
}
