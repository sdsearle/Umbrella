package com.nerdery.umbrella.data.api

import com.nerdery.umbrella.BuildConfig
import com.nerdery.umbrella.data.model.WeatherResponse
import com.nerdery.umbrella.data.model.TempUnit

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("/forecast/${BuildConfig.API_KEY}/{latitude},{longitude}")
    fun getWeather(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: TempUnit
    ): Single<WeatherResponse>

    @GET("/forecast/${BuildConfig.API_KEY}/{latitude},{longitude}")
    fun getWeatherCall(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double,
        @Query("units") units: TempUnit
    ): Call<WeatherResponse>
}