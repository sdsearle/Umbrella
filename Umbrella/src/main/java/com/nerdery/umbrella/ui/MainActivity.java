package com.nerdery.umbrella.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.data.ApiServicesProvider;
import com.nerdery.umbrella.data.ZipService;
import com.nerdery.umbrella.data.model.DarkSkyResponse;
import com.nerdery.umbrella.data.model.TempUnit;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText zipInput;
    private TextView weatherOutput;

    private ApiServicesProvider apiServicesProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiServicesProvider = new ApiServicesProvider(getApplication());

        zipInput = findViewById(R.id.zipInput);
        weatherOutput = findViewById(R.id.resultTextView);

        Button getWeatherButton = findViewById(R.id.getWeatherButton);
        getWeatherButton.setOnClickListener(v -> getWeather(zipInput.getText().toString()));
    }

    private void getWeather(String zipCode) {
        ZipService.getLatLongByZip(this, zipCode, new ZipService.ZipLocationListener() {
            @Override
            public void onLocationFound(ZipService.ZipLocation location) {
                apiServicesProvider.getDarkSkyService()
                        .getWeatherCall(location.getLatitude(), location.getLongitude(), TempUnit.FAHRENHEIT)
                        .enqueue(new Callback<DarkSkyResponse>() {
                            @Override
                            public void onResponse(Call<DarkSkyResponse> call, Response<DarkSkyResponse> response) {
                                Log.d("Dark Sky Response", response.toString());
                                DarkSkyResponse weather = response.body();
                                if (weather != null) {
                                    weatherOutput.setText(String.format(
                                            Locale.getDefault(),
                                            "%s, %d",
                                            weather.getCurrentForecast().getSummary(),
                                            Math.round(weather.getCurrentForecast().getTemp())
                                    ));
                                }
                            }

                            @Override
                            public void onFailure(Call<DarkSkyResponse> call, Throwable error) {
                                Log.e("Dark Sky", "Error Retrieving Weather", error);
                            }
                        });
            }

            @Override
            public void onLocationNotFound() {

            }
        });
    }

}