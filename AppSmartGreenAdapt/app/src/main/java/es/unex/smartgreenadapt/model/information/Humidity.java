package es.unex.smartgreenadapt.model.information;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.ArrayList;

public class Humidity {

    @SerializedName("message")
    public ArrayList<Message> message = new ArrayList<>();


    public int getIdTem() {
        return message.get(0).idTem;
    }

    public String getDate() {
        return message.get(0).date;
    }

    public void setDate(String date) {
        message.get(0).date = date;
    }

    public String getAmount() {
        return Float.toString(message.get(0).amount);
    }

    public void setAmount(int amount) {
        message.get(0).amount = amount;
    }

    public Humidity(String date, int amount) {
        message.get(0).date = date;
        message.get(0).amount = amount;
    }
}
