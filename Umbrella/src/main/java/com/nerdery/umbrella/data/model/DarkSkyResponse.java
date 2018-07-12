package com.nerdery.umbrella.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DarkSkyResponse {

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

    public class ForecastCondition {
        String summary;
        String icon;
        @SerializedName("temperature")
        double temp;
        Date time;

        public String getSummary() {
            return summary;
        }

        public String getIcon() {
            return icon;
        }

        public double getTemp() {
            return temp;
        }

        public Date getTime() {
            return time;
        }
    }

    private class HourlyResponse {
        @SerializedName("data")
        List<ForecastCondition> hours;

        public List<ForecastCondition> getHours() {
            return hours;
        }
    }

}
