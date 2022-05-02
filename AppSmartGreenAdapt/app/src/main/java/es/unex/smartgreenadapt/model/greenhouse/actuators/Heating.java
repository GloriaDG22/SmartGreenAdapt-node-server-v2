package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Heating {
    @SerializedName("message")
    public ArrayList<HeatingMessage> message = new ArrayList<>();

    public ArrayList<HeatingMessage> getAll() {
        return message;
    }

    public void setLists(ArrayList<HeatingMessage> message) {
        this.message = message;
    }
}
