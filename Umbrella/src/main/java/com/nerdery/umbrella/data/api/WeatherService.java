package com.nerdery.umbrella.data.api;

import com.nerdery.umbrella.BuildConfig;
import com.nerdery.umbrella.data.model.WeatherData;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit interface for fetching weather data
 */
public interface WeatherService {

    /**
     * Get the forecast for a given zip code using {@link Call}
     */
    @GET("/api/" + BuildConfig.API_KEY + "/conditions/hourly/q/{zip}.json")
    Call<WeatherData> forecastForZipCallable(@Path("zip") String zipCode);

    /**
     * Get the forecast for a given zip code using {@link Observable}
     */
    @GET("/api/" + BuildConfig.API_KEY + "/conditions/hourly/q/{zip}.json")
    Single<Result<WeatherData>> forecastForZipObservable(@Path("zip") String zipCode);
}