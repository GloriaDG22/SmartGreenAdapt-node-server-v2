package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import es.unex.smartgreenadapt.model.greenhouse.information.Message;

public class Windows {
    @SerializedName("message")
    public ArrayList<WindowMessage> message = new ArrayList<>();

    public ArrayList<WindowMessage> getAll() {
        return message;
    }

   public void setLists(ArrayList<WindowMessage> message) {
        this.message = message;
   }

}
