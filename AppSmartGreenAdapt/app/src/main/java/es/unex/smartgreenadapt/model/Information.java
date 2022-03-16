package es.unex.smartgreenadapt.model;

import java.sql.Date;

public class Information {

    private AirQuality airQuality;
    private Humidity humidity;
    private Luminosity luminosity;
    private Temperature temperature;

    public Date getAirQualityDate() {
        return airQuality.getDate();
    }

    public String getAirQualityAmount() {
        return airQuality.getAmount();
    }

    public Date getHumidityDate() {
        return humidity.getDate();
    }

    public String getHumidityAmount() {
        return humidity.getAmount();
    }

    public Date getLuminosityDate() {
        return luminosity.getDate();
    }

    public String getLuminosityAmount() {
        return luminosity.getAmount();
    }

    public String getTemperatureDate() {
        return temperature.getDate();
    }

    public String getTemperatureAmount() {
        return temperature.getAmount();
    }
}
