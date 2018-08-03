package com.nerdery.umbrella.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
