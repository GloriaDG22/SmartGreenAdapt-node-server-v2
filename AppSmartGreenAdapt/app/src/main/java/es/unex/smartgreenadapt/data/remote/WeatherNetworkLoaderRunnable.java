package es.unex.smartgreenadapt.data.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetworkLoaderRunnable {

    private static WeatherNetworkLoaderRunnable mInstance;
    private Retrofit mRetrofit;
    private String URLBase = "https://api.openweathermap.org/";
    //"https://api.openweathermap.org/data/2.5/weather?lat=39.4789255&lon=-6.3423358&appid='0db5d912986a7c8d9443ea0ccc7e19b8'";

    public WeatherNetworkLoaderRunnable(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WeatherNetworkLoaderRunnable getInstance(){
        if(mInstance == null){
            mInstance = new WeatherNetworkLoaderRunnable();
        }
        return mInstance;
    }

    public APIService getApi(){
        return mRetrofit.create(APIService.class);
    }
}

