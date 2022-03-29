package es.unex.smartgreenadapt.data.remote;

import java.util.ArrayList;

import es.unex.smartgreenadapt.model.Notification;
import es.unex.smartgreenadapt.model.information.Humidity;
import es.unex.smartgreenadapt.model.information.Luminosity;
import es.unex.smartgreenadapt.model.information.AirQuality;
import es.unex.smartgreenadapt.model.WeatherResponse;
import es.unex.smartgreenadapt.model.information.Temperature;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("data/2.5/weather?units=metric")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

    @GET("notification")
    Call<Notification> getNotifications();

    @GET("temperature")
    Call<Temperature> getCurrentTemperature();

    @GET("luminosity")
    Call<Luminosity> getCurrentLuminosity();

    @GET("airquality")
    Call<AirQuality> getCurrentAirQuality();

    @GET("humidity")
    Call<Humidity> getCurrentHumidity();
}

