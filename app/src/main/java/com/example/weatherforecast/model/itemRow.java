package com.example.weatherforecast.model;

public class itemRow {
    private String day, hour, humidity, temperature, realFeel, droplets;
    private int drop;

    public itemRow(String day, String hour, String humidity, String temperature, String realFeel, String droplets, int drop) {
        this.day = day;
        this.hour = hour;
        this.humidity = humidity;
        this.temperature = temperature;
        this.realFeel = realFeel;
        this.droplets = droplets;
        this.drop = drop;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(String realFeel) {
        this.realFeel = realFeel;
    }

    public String getDroplets() {
        return droplets;
    }

    public void setDroplets(String droplets) {
        this.droplets = droplets;
    }

    public int getDrop() {
        return drop;
    }

    public void setDrop(int drop) {
        this.drop = drop;
    }
}
