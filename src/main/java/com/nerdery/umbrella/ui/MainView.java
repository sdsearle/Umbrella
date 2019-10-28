package com.nerdery.umbrella.ui;

import android.app.Application;

import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.TempUnit;

import java.util.List;

public interface MainView {

    Application getApp();

    void openSettings();

    void updateRecyclerViews(List<ForecastCondition> todayList, List<ForecastCondition> tomorrowList);

    void updateCurrentWeather(ForecastCondition currentForecast, TempUnit unit);

    void setLocation(String city);
}
