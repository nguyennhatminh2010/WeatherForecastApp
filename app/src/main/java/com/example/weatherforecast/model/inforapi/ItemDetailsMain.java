package com.example.weatherforecast.model.inforapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemDetailsMain {
    //Nhiet do
    @SerializedName("temp")
    @Expose
    private Double temp;

    //Nhiet do thich hop
    @SerializedName("feels_like")
    @Expose
    private Double feelsLike;

    //Nhiet do thap nhat trong gio
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;

    //Nhiet do cao nhat trong gio
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

    //Ap suat tren muc nuoc bien
    @SerializedName("pressure")
    @Expose
    private Integer pressure;

    //ko can quan tam
    @SerializedName("sea_level")
    @Expose
    private Integer seaLevel;

    //Ap suat tren mat dat
    @SerializedName("grnd_level")
    @Expose
    private Integer grndLevel;

    //Độ ẩm
    @SerializedName("humidity")
    @Expose
    private Integer humidity;


    @SerializedName("temp_kf")
    @Expose
    private Double tempKf;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(Integer seaLevel) {
        this.seaLevel = seaLevel;
    }

    public Integer getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(Integer grndLevel) {
        this.grndLevel = grndLevel;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getTempKf() {
        return tempKf;
    }

    public void setTempKf(Double tempKf) {
        this.tempKf = tempKf;
    }

}
