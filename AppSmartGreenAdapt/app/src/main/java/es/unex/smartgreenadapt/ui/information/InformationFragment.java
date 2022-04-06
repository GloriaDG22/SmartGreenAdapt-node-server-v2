package es.unex.smartgreenadapt.ui.information;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.sql.Date;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.data.remote.WeatherNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.greenhouse.Greenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;
import es.unex.smartgreenadapt.model.greenhouse.information.AirQuality;
import es.unex.smartgreenadapt.model.greenhouse.information.Humidity;
import es.unex.smartgreenadapt.model.greenhouse.information.Luminosity;
import es.unex.smartgreenadapt.model.greenhouse.WeatherResponse;
import es.unex.smartgreenadapt.model.greenhouse.information.Temperature;
import es.unex.smartgreenadapt.ui.notifications.NotificationsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationFragment extends Fragment {

    private TextView temperature, luminosity, humidity, airquality, dateW, stateW, temp, feels_likeW, tempMin, tempMax, pressure, humidityW, wind;

    private WeatherNetworkLoaderRunnable mWheatherNet;
    private InformationNetworkLoaderRunnable mInformNet;

    private MessageGreenhouse mGreenhouse;

    private static String lat = "39.4789255";
    private static String lon= "-6.3423358";
    private static String appid="0db5d912986a7c8d9443ea0ccc7e19b8";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_information, container, false);

        //First table
        temperature = root.findViewById(R.id.value_temperature);
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

        Bundle bundle = getActivity().getIntent().getExtras();
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable(GreenhouseActivity.EXTRA_GREENHOUSE);

        getActivity().setTitle(mGreenhouse.getName());

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
                    Date date = new Date((long) (currentWeather.getDt()*1000));
                    dateW.setText(date.toString());
                    stateW.setText(currentWeather.getWeatherState());
                    temp.setText(currentWeather.getMainTemp() + "º");
                    feels_likeW.setText(currentWeather.getMainFeelsLike() + "º");
                    tempMin.setText(currentWeather.getMainTemp_min() + "º");
                    tempMax.setText(currentWeather.getMainTemp_max() + "º");
                    pressure.setText(currentWeather.getMainPressure() + " hPa");
                    humidityW.setText(currentWeather.getMainHumidity() + " %");
                    wind.setText(currentWeather.getWindSpeed() + " m/s");
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

        //TEMPERATURE
        Call<Temperature> responseTem = mInformNet.getApi().getCurrentTemperature(mGreenhouse.getId());
        responseTem.enqueue(new Callback<Temperature>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {
                if (response.isSuccessful()) {
                    Temperature currentTem = response.body();
                    temperature.setText(currentTem.getAmount());
                }
            }
            @Override
            public void onFailure(Call<Temperature> call, Throwable t) {
                //vacio
            }
        });

        //LUMINOSITY
        Call<Luminosity> responseLum = mInformNet.getApi().getCurrentLuminosity(mGreenhouse.getId());
        responseLum.enqueue(new Callback<Luminosity>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Luminosity> call, @NonNull Response<Luminosity> response) {
                if (response.isSuccessful()) {
                    Luminosity currentLum = response.body();
                    luminosity.setText(currentLum.getAmount());
                }
            }

            @Override
            public void onFailure(Call<Luminosity> call, Throwable t) {
                //vacio
            }
        });

        //AIRQUIALITY
        Call<AirQuality> responseAir = mInformNet.getApi().getCurrentAirQuality(mGreenhouse.getId());
        responseAir.enqueue(new Callback<AirQuality>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<AirQuality> call, @NonNull Response<AirQuality> response) {
                if (response.isSuccessful()) {
                    AirQuality currentAir = response.body();
                    airquality.setText(currentAir.getAmount());
                }
            }

            @Override
            public void onFailure(Call<AirQuality> call, Throwable t) {
                //vacio
            }
        });

        //HUMIDITY
        Call<Humidity> responseHum = mInformNet.getApi().getCurrentHumidity(mGreenhouse.getId());
        responseHum.enqueue(new Callback<Humidity>() {
            @Override
            public void onResponse(@NonNull Call<Humidity> call, @NonNull Response<Humidity> response) {
                if (response.isSuccessful()) {
                    Humidity currentHum = response.body();
                    humidity.setText(currentHum.getAmount());
                }
            }

            @Override
            public void onFailure(Call<Humidity> call, Throwable t) {
                //vacio
            }
        });
    }
}