package com.example.weatherforecast.model.database;

import androidx.room.TypeConverter;

import com.example.weatherforecast.model.api.IdApiCall;
import com.google.gson.Gson;

public class SampleDbCoverter {
    @TypeConverter
    public static String fromIdApiCall(IdApiCall value){
        Gson gson = new Gson();
        String json = gson.toJson(value);
        return json;
    }

    @TypeConverter
    public static IdApiCall fromString(String value){
        Gson gson = new Gson();

        IdApiCall result = gson.fromJson(value, IdApiCall.class);
        return result;
    }



}
