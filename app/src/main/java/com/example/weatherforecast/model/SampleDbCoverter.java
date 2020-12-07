package com.example.weatherforecast.model;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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
