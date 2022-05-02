package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Irrigation {
    @SerializedName("message")
    public ArrayList<ActuatorMessage> message = new ArrayList<>();

    public ArrayList<ActuatorMessage> getAll() {
        return message;
    }

    public void setLists(ArrayList<ActuatorMessage> message) {
        this.message = message;
    }
}
