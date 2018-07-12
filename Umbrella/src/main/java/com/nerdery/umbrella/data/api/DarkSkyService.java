package com.nerdery.umbrella.data.api;

import com.nerdery.umbrella.data.model.DarkSkyResponse;
import com.nerdery.umbrella.data.model.TempUnit;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DarkSkyService {

    @GET("/{latitude},{longitude}")
    Single<DarkSkyResponse> getWeather(@Path("latitude") double latitude,
                                       @Path("longitude") double longitude,
                                       @Query("units") TempUnit units);

    @GET("/forecast/7596ecba2f0d6397a4023a423c7fcd1d/{latitude},{longitude}")
    Call<DarkSkyResponse> getWeatherCall(@Path("latitude") double latitude,
                                         @Path("longitude") double longitude,
                                         @Query("units") TempUnit units);

}
