package com.example.weatherforecast.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("3h")
    @Expose
    private Double _3h;

    public Double get3h() {
//        if (Double.isNaN(_3h)) {
//            return 0.0;
//        }
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }

}
