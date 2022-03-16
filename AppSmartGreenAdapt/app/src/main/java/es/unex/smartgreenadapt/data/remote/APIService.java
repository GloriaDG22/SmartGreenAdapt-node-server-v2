package es.unex.smartgreenadapt.data.remote;

import java.util.ArrayList;

import es.unex.smartgreenadapt.model.Information;
import es.unex.smartgreenadapt.model.Luminosity;
import es.unex.smartgreenadapt.model.Notification;
import es.unex.smartgreenadapt.model.Temperature;
import es.unex.smartgreenadapt.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("data/2.5/weather?units=metric")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

    @GET("notification")
    Call<ArrayList<Notification>> listNotification();

    @GET("temperature?")
    Call<Temperature> getCurrentInformation(@Query("date") String date);

}

