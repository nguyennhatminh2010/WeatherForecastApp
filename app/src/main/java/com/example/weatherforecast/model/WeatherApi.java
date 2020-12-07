package com.example.weatherforecast.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/forecast?lang=vi&appid=9bc8c7f50a54ca8be44dcc802043b675")
    Single<IdApiCall> getAPI(@Query("lat") double lat, @Query("lon") double lon);


}
