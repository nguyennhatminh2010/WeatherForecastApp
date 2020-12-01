package com.example.weatherforecast.viewmodel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherforecast.model.SampleApiCall;

import java.util.List;

@Dao
public interface SampleDao {
    @Query("SELECT * FROM SampleApiCall")
    public List<SampleApiCall> getAllSampleApiCall();

    @Insert
    public void insertSampleApiCall(SampleApiCall... sampleApiCalls);
}
