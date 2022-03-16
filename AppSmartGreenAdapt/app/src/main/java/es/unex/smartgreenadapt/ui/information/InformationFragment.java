package es.unex.smartgreenadapt.ui.information;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.sql.Date;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.data.remote.WeatherNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.AirQuality;
import es.unex.smartgreenadapt.model.Humidity;
import es.unex.smartgreenadapt.model.Luminosity;
import es.unex.smartgreenadapt.model.Temperature;
import es.unex.smartgreenadapt.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationFragment extends Fragment {

    private TextView temperature, feels_like, luminosity, humidity, airquality, dateW, stateW, temp, feels_likeW, tempMin, tempMax, pressure, humidityW, wind;

    private WeatherNetworkLoaderRunnable mWheatherNet;
    private InformationNetworkLoaderRunnable mInformNet;

    private static String lat = "39.4789255";
    private static String lon= "-6.3423358";
    private static String appid="0db5d912986a7c8d9443ea0ccc7e19b8";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_information, container, false);

        //First table
        temperature = root.findViewById(R.id.value_temperature);
        feels_like = root.findViewById(R.id.value_thermal_sensation);
        luminosity = root.findViewById(R.id.value_luminosity);
        humidity = root.findViewById(R.id.value_humidity);
        airquality = root.findViewById(R.id.value_airquality);

        //Table horizontal (weather)
        dateW = root.findViewById(R.id.value_date);
        stateW = root.findViewById(R.id.value_state);
        temp= root.findViewById(R.id.value_temp);
        feels_likeW = root.findViewById(R.id.value_thermal_sensationW);
        tempMin = root.findViewById(R.id.value_tempMin);
        tempMax = root.findViewById(R.id.value_tempMax);
        pressure = root.findViewById(R.id.value_pressure);
        humidityW = root.findViewById(R.id.value_humidityW);
        wind = root.findViewById(R.id.value_wind);

        //Llamada a la APi de los ultimos datos
        //TODO
        AirQuality a = new AirQuality(Date.valueOf("2022-02-22"), 20);
        Humidity h = new Humidity(Date.valueOf("2022-02-22"), 20);
        Luminosity l = new Luminosity(Date.valueOf("2022-02-22"), 20);

        luminosity.setText(l.getAmount());
        humidity.setText(h.getAmount());
        airquality.setText(a.getAmount());

        //call API weather
        getCurrentWeather();

        getCurrentInformation();

        return root;
    }

    public void getCurrentWeather(){
        mWheatherNet = WeatherNetworkLoaderRunnable.getInstance();

        Call<WeatherResponse> responseWeather = mWheatherNet.getApi().getCurrentWeatherData(lat, lon, appid);

        responseWeather.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse currentWeather = response.body();
                    //Log.println(Log.ASSERT, "info", "El dato es" + response.body());
                    Date date = new Date((long) (currentWeather.getDt()*1000));
                    dateW.setText(date.toString());
                    stateW.setText(currentWeather.getWeatherState());
                    temp.setText(Float.toString(currentWeather.getMainTemp()));
                    feels_likeW.setText(Float.toString(currentWeather.getMainFeelsLike()));
                    tempMin.setText(Float.toString(currentWeather.getMainTemp_min()));
                    tempMax.setText(Float.toString(currentWeather.getMainTemp_max()));
                    pressure.setText(Float.toString(currentWeather.getMainPressure()));
                    humidityW.setText(Float.toString(currentWeather.getMainHumidity()));
                    wind.setText(Float.toString(currentWeather.getWindSpeed()));
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                //vacio
            }
        });
    }

    public void getCurrentInformation(){
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Call<Temperature> responseTem = mInformNet.getApi().getCurrentInformation("2019-01-10 13:12:35");

        Log.println(Log.ASSERT, "info", "El dato es" + responseTem.isExecuted());
        responseTem.enqueue(new Callback<Temperature>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {
                if (response.isSuccessful()) {
                    Temperature currentTem = response.body();
                    Log.println(Log.ASSERT, "info", "El dato es" + response.body());
                    temperature.setText(currentTem.getAmount());
                }
            }

            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                //vacio
            }
        });
    }
}