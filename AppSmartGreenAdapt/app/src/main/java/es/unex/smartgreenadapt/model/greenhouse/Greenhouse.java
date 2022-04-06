package es.unex.smartgreenadapt.model.greenhouse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Greenhouse implements Serializable {

    @SerializedName("message")
    public ArrayList<MessageGreenhouse> message = new ArrayList<>();

    public ArrayList<MessageGreenhouse> getList() {
        return message;
    }

    public void setList(ArrayList<MessageGreenhouse> message) {
        this.message = message;
    }

    public void addMessage(MessageGreenhouse message){ this.message.add(message);}
}
