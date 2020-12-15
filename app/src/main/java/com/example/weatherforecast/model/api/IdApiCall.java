package com.example.weatherforecast.model.api;

import com.example.weatherforecast.model.inforapi.City;
import com.example.weatherforecast.model.inforapi.ListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
//Id cua lan goi Api
public class IdApiCall {
    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("message")
    @Expose
    private Integer message;

    //So mau lay duoc tu api(5 ngay, 3 tieng => 40 mau)
    @SerializedName("cnt")
    @Expose
    private Integer cnt;

    //Thong tin tung gio(3 gio 1 lan)
    @SerializedName("list")
    @Expose
    private ArrayList<ListItem> listItem = null;

    @SerializedName("city")
    @Expose
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public ArrayList<ListItem> getListItem() {
        return listItem;
    }

    public void setListItem(ArrayList<ListItem> listItem) {
        this.listItem = listItem;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
