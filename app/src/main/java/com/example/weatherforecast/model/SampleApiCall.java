package com.example.weatherforecast.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SampleApiCall")
public class SampleApiCall {

    @PrimaryKey(autoGenerate = true)
    private int id ;

    @ColumnInfo(name = "IdApiCall")
    private IdApiCall idApiCall;

    public SampleApiCall(IdApiCall idApiCall) {
        this.idApiCall = idApiCall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IdApiCall getIdApiCall() {
        return idApiCall;
    }

    public void setIdApiCall(IdApiCall idApiCall) {
        this.idApiCall = idApiCall;
    }
}
