package es.unex.smartgreenadapt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import es.unex.smartgreenadapt.model.information.Message;

public class Notification {

    @SerializedName("message")
    public ArrayList<MessageNotification> message = new ArrayList<>();

    public ArrayList<MessageNotification> getList() {
        return message;
    }

    public void setList(ArrayList<MessageNotification> message) {
        this.message = message;
    }
}

