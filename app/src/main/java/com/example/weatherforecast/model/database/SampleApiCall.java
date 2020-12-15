package com.example.weatherforecast.model.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.weatherforecast.model.api.IdApiCall;

@Entity(tableName = "SampleApiCall")
public class SampleApiCall {

    @PrimaryKey(autoGenerate = true)
    private int id ;

    @ColumnInfo(name = "Place")
    private String place;

    @ColumnInfo(name = "IdApiCall")
    private IdApiCall idApiCall;

    public SampleApiCall(String place, IdApiCall idApiCall) {
        this.place = place;
        this.idApiCall = idApiCall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public IdApiCall getIdApiCall() {
        return idApiCall;
    }

    public void setIdApiCall(IdApiCall idApiCall) {
        this.idApiCall = idApiCall;
    }
}
