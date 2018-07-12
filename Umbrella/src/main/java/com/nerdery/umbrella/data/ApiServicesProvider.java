package com.nerdery.umbrella.data;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.nerdery.umbrella.data.api.DarkSkyService;
import com.nerdery.umbrella.data.api.IconApi;
import com.nerdery.umbrella.data.api.WeatherService;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import javax.annotation.Nullable;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;

/**
 * Provides {@link Picasso}, {@link WeatherService}, and {@link IconApi}
 * that are all ready setup and ready to use.
 */
public final class ApiServicesProvider {

  private static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);

  private final IconApi iconApi;
  private final WeatherService weatherService;
  private final DarkSkyService darkSkyService;
  private final Picasso picasso;

  /**
   * Constructor.
   *
   * @param application application context used for creating network caches.
   */
  public ApiServicesProvider(Application application) {
    iconApi = new IconApi();

    OkHttpClient client = createOkHttpClient(application);
    Moshi moshi = provideMoshi();

    weatherService = provideRetrofit(client, moshi).create(WeatherService.class);
    darkSkyService = provideDarkSkyRetrofit(client, provideGson()).create(DarkSkyService.class);

    picasso = new Picasso.Builder(application)
        .downloader(new OkHttp3Downloader(client))
        .listener((picasso, uri, e) -> Timber.e(e, "Failed to load image: %s", uri))
        .build();
  }

  public Picasso getPicasso() {
    return picasso;
  }

  /** @return ready to use {@link IconApi} */
  public IconApi getIconApi() {
    return iconApi;
  }

  /** @return an instance of the {@link WeatherService} service that is ready to use. */
  public WeatherService getWeatherService() {
    return weatherService;
  }

  private Retrofit provideDarkSkyRetrofit(OkHttpClient client, Gson gson) {
    return new Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.darksky.net/forecast/7596ecba2f0d6397a4023a423c7fcd1d/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
  }

  private Gson provideGson() {
      GsonBuilder gson = new GsonBuilder();
      gson.registerTypeAdapter(Date.class, new DateDeserializer());
      return gson.create();
  }

  private Retrofit provideRetrofit(OkHttpClient client, Moshi moshi) {
    return new Retrofit.Builder()
        .client(client)
        .baseUrl("http://api.wunderground.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  private OkHttpClient createOkHttpClient(Application app) {
    // Install an HTTP cache in the application cache directory.
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

    return new OkHttpClient.Builder()
        .cache(cache)
        .build();
  }

  private Moshi provideMoshi() {
    return new Moshi.Builder()
        .add(new ForecastConditionAdapter())
        .add(MoshiAdapterFactory.create())
        .build();
  }

    public DarkSkyService getDarkSkyService() {
        return darkSkyService;
    }
}
