package es.unex.smartgreenadapt.model.greenhouse.information;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Humidity {

    @SerializedName("message")
    public ArrayList<Message> message = new ArrayList<>();


    public int getIdTem() {
        return message.get(0).id;
    }

    public String getDate() {
        return message.get(0).date;
    }

    public void setDate(String date) {
        message.get(0).date = date;
    }

    public String getAmount() {
        String humidity = message == null ? "-" : Float.toString(message.get(0).amount);
        return humidity;
    }

    public void setAmount(int amount) {
        message.get(0).amount = amount;
    }

    public int getIdGreenhouse() {
        return message.get(0).idGreenhouse;
    }

    public Humidity(String date, float amount) {
        message.get(0).date = date;
        message.get(0).amount = amount;
    }
}
