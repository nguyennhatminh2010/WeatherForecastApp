package com.example.weatherforecast.viewmodel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherforecast.model.api.IdApiCall;
import com.example.weatherforecast.model.database.SampleApiCall;

import java.util.List;

@Dao
public interface SampleDao {
    @Query("SELECT * FROM SampleApiCall")
    public List<SampleApiCall> getAllSampleApiCall();

    @Query("SELECT * FROM SampleApiCall WHERE Place LIKE :name")
    public List<SampleApiCall> getSpecificSampleApiCall(String name);

    @Query("UPDATE SampleApiCall SET IdApiCall = :idApiCall WHERE Place LIKE :name")
    public void updateSampleApiCall(String name, IdApiCall idApiCall);

    @Insert
    public void insertSampleApiCall(SampleApiCall... sampleApiCalls);
}
