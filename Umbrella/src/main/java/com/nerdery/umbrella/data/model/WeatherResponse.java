package com.nerdery.umbrella.data.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("timezone")
    String timeZone;

    @SerializedName("currently")
    ForecastCondition currentForecast;

    HourlyResponse hourly;

    public String getTimeZone() {
        return timeZone;
    }

    public ForecastCondition getCurrentForecast() {
        return currentForecast;
    }
}
