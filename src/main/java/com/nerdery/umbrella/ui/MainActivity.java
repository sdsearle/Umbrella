package com.nerdery.umbrella.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.data.model.ForecastCondition;
import com.nerdery.umbrella.data.model.TempUnit;
import com.nerdery.umbrella.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mainPresenter;
    MyWeatherRecyclerViewAdapter todayRecyclerAdapter;
    MyWeatherRecyclerViewAdapter tomorrowRecyclerAdapter;
    private RecyclerView rvToday;
    private RecyclerView rvTomorrow;
    private TextView tvCurrentTemp;
    private TextView tvCurrentCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        //Action Bar
        getSupportActionBar().setElevation(0);

        mainPresenter = new MainPresenter(this);
        rvToday = findViewById(R.id.rvToday);
        rvTomorrow = findViewById(R.id.rvTomorrow);
        rvToday.setNestedScrollingEnabled(false);
        rvTomorrow.setNestedScrollingEnabled(false);
        rvToday.setLayoutManager(new GridLayoutManager(this, 4));
        rvTomorrow.setLayoutManager(new GridLayoutManager(this, 4));
        tvCurrentCondition = findViewById(R.id.tv_current_condition);
        tvCurrentTemp = findViewById(R.id.tv_current_temp);

        todayRecyclerAdapter = new MyWeatherRecyclerViewAdapter(new ArrayList<>());
        tomorrowRecyclerAdapter = new MyWeatherRecyclerViewAdapter(new ArrayList<>());
        rvToday.setAdapter(todayRecyclerAdapter);
        rvTomorrow.setAdapter(tomorrowRecyclerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //if location is empty then got to settings
        mainPresenter.getLocation(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //Go to settings
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        openSettings();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Application getApp() {
        return getApplication();
    }

    @Override
    public void openSettings() {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateRecyclerViews(List<ForecastCondition> todayList, List<ForecastCondition> tomorrowList) {
        todayRecyclerAdapter.updateData(todayList);
        tomorrowRecyclerAdapter.updateData(tomorrowList);
        Timber.d("updateRecyclerViews: " + tomorrowRecyclerAdapter.getItemCount());
    }

    @Override
    public void updateCurrentWeather(ForecastCondition currentForecast, TempUnit unit) {
        if(currentForecast != null) {
            tvCurrentCondition.setText(currentForecast.getSummary());
            String currentTemp = (int) (currentForecast.getTemp()) + "°";
            tvCurrentTemp.setText(currentTemp);
            if((currentForecast.getTemp() < 60 && unit == TempUnit.FAHRENHEIT) || (currentForecast.getTemp() < 16 && unit == TempUnit.CELSIUS)){
                getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.weather_cool));
                tvCurrentTemp.setBackground(getDrawable(R.color.weather_cool));
                tvCurrentCondition.setBackground(getDrawable(R.color.weather_cool));
            }else{
                getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.weather_warm));
                tvCurrentTemp.setBackground(getDrawable(R.color.weather_warm));
                tvCurrentCondition.setBackground(getDrawable(R.color.weather_warm));
            }
        }
    }

    @Override
    public void setLocation(String city) {
        setTitle(city);
    }
}
