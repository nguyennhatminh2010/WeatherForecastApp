package com.example.weatherforecast.viewmodel;

import com.example.weatherforecast.model.IdApiCall;
import com.example.weatherforecast.model.WeatherApi;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiService {
    private static final String BASE_URL = "http://api.openweathermap.org/";
    private WeatherApi api;
    public WeatherApiService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(WeatherApi.class);
    }
    public Single<IdApiCall> getAPI(){
        return api.getAPI();
    }
}
