package com.nerdery.umbrella.presenter;

import android.content.Context;
import android.preference.PreferenceManager;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.data.ApiServicesProvider;
import com.nerdery.umbrella.data.ZipCodeService;
import com.nerdery.umbrella.data.ZipLocation;
import com.nerdery.umbrella.data.ZipLocationListener;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.TempUnit;
import com.nerdery.umbrella.data.model.WeatherResponse;
import com.nerdery.umbrella.ui.MainView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter {

    private MainView mainView;

    private List<ForecastCondition> forecastConditionList;
    private List<ForecastCondition> todayList;
    private List<ForecastCondition> tomorrowList;
    private String zipcode;
    private ForecastCondition currentCondition;


    public MainPresenter(MainView mainView){
        this.mainView = mainView;
        todayList = new ArrayList<>();
        tomorrowList = new ArrayList<>();
    }

    public void getLocation(Context context) {
        //getZip from preferences
        zipcode = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.zip), "");
        if(zipcode.isEmpty()){
            mainView.openSettings();
        }

        ZipCodeService.INSTANCE.getLatLongByZip(context, zipcode, new ZipLocationListener() {
            @Override
            public void onLocationFound(@NotNull ZipLocation location) {
                TempUnit unit = TempUnit.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.unit), "us"));
                getWeather(location, unit);
                String city = location.getCity() + ", " + location.getState();
                mainView.setLocation(city);
            }

            @Override
            public void onLocationNotFound() {
                mainView.openSettings();
            }
        });
        //return zipcode
    }

    public void getWeather(ZipLocation location, TempUnit unit) {
        ApiServicesProvider apiServicesProvider = new ApiServicesProvider(mainView.getApp());

        //reset lists
        todayList.clear();
        tomorrowList.clear();

        apiServicesProvider.getWeatherService().getWeatherCall(location.getLatitude(), location.getLongitude(), unit).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Timber.log(1, "Weather call %s", response.toString());
                currentCondition = response.body().getCurrentForecast();
                forecastConditionList = response.body().getHourly().getHours();
                splitDays(forecastConditionList, Calendar.getInstance().getTime(), 0, todayList);
                mainView.updateCurrentWeather(currentCondition, unit);
                mainView.updateRecyclerViews(todayList, tomorrowList);

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });

    }

    private void splitDays(List<ForecastCondition> forecastConditionList, Date date, int start, List<ForecastCondition> currentList) {
        //get Today's date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int today = calendar.get(Calendar.DATE);
        for (int i = start; i < forecastConditionList.size(); i++) {
            //check if date is tomorrow
            calendar.setTime(forecastConditionList.get(i).getTime());
            if(today != calendar.get(Calendar.DATE)){
                if(start == 0) {
                    start = i;
                    splitDays(forecastConditionList, calendar.getTime(), start, tomorrowList);
                }
                break;
            }else{
                currentList.add(forecastConditionList.get(i));
            }
        }
    }
}
