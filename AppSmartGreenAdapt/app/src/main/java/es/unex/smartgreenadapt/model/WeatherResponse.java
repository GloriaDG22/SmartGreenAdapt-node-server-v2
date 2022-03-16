package es.unex.smartgreenadapt.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

        @SerializedName("coord")
        private Coord coord;
        @SerializedName("sys")
        public Sys sys;
        @SerializedName("weather")
        public ArrayList<Weather> weather = new ArrayList<Weather>();
        @SerializedName("main")
        public Main main;
        @SerializedName("wind")
        public Wind wind;
        @SerializedName("rain")
        public Rain rain;
        @SerializedName("clouds")
        public Clouds clouds;
        @SerializedName("dt")
        public float dt;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("cod")
        public float cod;


    public String getWeatherState() {
        return weather.get(0).main;
    }

    public float getMainTemp() {
        return main.temp;
    }

    public float getMainFeelsLike() {
        return main.feels_like;
    }

    public float getMainHumidity() {
        return main.humidity;
    }

    public float getMainPressure() {
        return main.pressure;
    }

    public float getMainTemp_min() {
        return main.temp_min;
    }

    public float getMainTemp_max() {
        return main.temp_max;
    }

    public Float getWindSpeed() {
        return wind.speed;
    }

    public float getDt() {
        return dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}

    class Weather {
        @SerializedName("id")
        public int id;
        @SerializedName("main")
        public String main;
        @SerializedName("description")
        public String description;
        @SerializedName("icon")
        public String icon;
    }

    class Clouds {
        @SerializedName("all")
        public float all;
    }

    class Rain {
        @SerializedName("3h")
        public float h3;
    }

    class Wind {
        @SerializedName("speed")
        public float speed;
        @SerializedName("deg")
        public float deg;
    }

    class Main {
        @SerializedName("temp")
        public float temp;
        @SerializedName("feels_like")
        public float feels_like;
        @SerializedName("humidity")
        public float humidity;
        @SerializedName("pressure")
        public float pressure;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;
    }

    class Sys {
        @SerializedName("country")
        public String country;
        @SerializedName("sunrise")
        public long sunrise;
        @SerializedName("sunset")
        public long sunset;
    }

    class Coord {
        @SerializedName("lon")
        public float lon;
        @SerializedName("lat")
        public float lat;
    }



