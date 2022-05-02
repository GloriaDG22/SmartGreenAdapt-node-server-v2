package es.unex.smartgreenadapt.data.remote;

import java.util.List;

import es.unex.smartgreenadapt.data.LoginResponse;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.Greenhouse;
import es.unex.smartgreenadapt.model.greenhouse.Notification;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Heating;
import es.unex.smartgreenadapt.model.greenhouse.actuators.HeatingMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Irrigation;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Sprinklers;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Windows;
import es.unex.smartgreenadapt.model.greenhouse.information.Humidity;
import es.unex.smartgreenadapt.model.greenhouse.information.Luminosity;
import es.unex.smartgreenadapt.model.greenhouse.information.AirQuality;
import es.unex.smartgreenadapt.model.greenhouse.WeatherResponse;
import es.unex.smartgreenadapt.model.greenhouse.information.Temperature;
import es.unex.smartgreenadapt.model.login.MessageUser;
import es.unex.smartgreenadapt.model.login.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIService {

    @GET("data/2.5/weather?units=metric")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

    @GET("notification")
    Call<Notification> getNotifications(@Query("idGreenhouse") int idGreenhouse);

    @GET("temperature")
    Call<Temperature> getCurrentTemperature(@Query("idGreenhouse") int idGreenhouse);

    @GET("luminosity")
    Call<Luminosity> getCurrentLuminosity(@Query("idGreenhouse") int idGreenhouse);

    @GET("airquality")
    Call<AirQuality> getCurrentAirQuality(@Query("idGreenhouse") int idGreenhouse);

    @GET("humidity")
    Call<Humidity> getCurrentHumidity(@Query("idGreenhouse") int idGreenhouse);

    @GET("users")
    Call<User> users();

    @POST("users")
    Call<MessageResponse> postUser(@Body MessageUser user);

    @GET("greenhouse")
    Call<Greenhouse> getGreenhouses(@Query("idUsuario") int idUsuario);

    @GET("windows")
    Call<Windows> getWindows(@Query("idGreenhouse") int idGreenhouse);

    @POST("windows")
    Call<MessageResponse> postWindow(@Body WindowMessage window);

    @PUT("windows")
    Call<MessageResponse> putWindow(@Body WindowMessage window);

    @GET("heatings")
    Call<Heating> getHeating(@Query("idGreenhouse") int idGreenhouse);

    @POST("heatings")
    Call<MessageResponse> postHeating(@Body HeatingMessage heating);

    @PUT("heatings")
    Call<MessageResponse> putHeating(@Body HeatingMessage heating);

    @GET("irrigation")
    Call<Irrigation> getIrrigation(@Query("idGreenhouse") int Greenhouse);

    @POST("irrigation")
    Call<MessageResponse> postIrrigation(@Body ActuatorMessage irrigation);

    @PUT("irrigation")
    Call<MessageResponse> putIrrigation(@Body ActuatorMessage irrigation);

    @GET("sprinklers")
    Call<Sprinklers> getSprinklers(@Query("idGreenhouse") int Greenhouse);

    @POST("sprinklers")
    Call<MessageResponse> postSprinklers(@Body ActuatorMessage sprinkler);

    @PUT("sprinklers")
    Call<MessageResponse> putSprinklers(@Body ActuatorMessage sprinkler);

}

