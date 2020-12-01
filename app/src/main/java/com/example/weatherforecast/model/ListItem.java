package com.example.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListItem implements Serializable {
    @SerializedName("dt")
    @Expose
    private Integer dt;

    //Thông tin tổng quát của ngay nay
    @SerializedName("main")
    @Expose
    private ItemDetailsMain main;

    //Thời tiết hiện tại của giờ này
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;

    //Mây
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;

    //Gió
    @SerializedName("wind")
    @Expose
    private Wind wind;

    //Tầm nhìn (mét)
    @SerializedName("visibility")
    @Expose
    private Integer visibility;

    //Xác suất mưa
    @SerializedName("pop")
    @Expose
    private Double pop;

    @SerializedName("rain")
    @Expose
    private Rain rain;

    //Ngày hoặc đêm(ngày - d, đêm - n)
    @SerializedName("sys")
    @Expose
    private Sys sys;

    //"2020-11-28 00:00:00" hiển thị múi giờ
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public ListItem(Integer dt, ItemDetailsMain main, List<Weather> weather, Clouds clouds, Wind wind, Integer visibility, Double pop, Rain rain, Sys sys, String dtTxt) {
        this.dt = dt;
        this.main = main;
        this.weather = weather;
        this.clouds = clouds;
        this.wind = wind;
        this.visibility = visibility;
        this.pop = pop;
        this.rain = rain;
        this.sys = sys;
        this.dtTxt = dtTxt;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public ItemDetailsMain getMain() {
        return main;
    }

    public void setMain(ItemDetailsMain main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
