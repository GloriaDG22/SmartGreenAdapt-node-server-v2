package es.unex.smartgreenadapt.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Luminosity {

    @SerializedName(value = "id")
    private int idLum;

    @SerializedName("date")
    private Date date;

    @SerializedName("amount")
    private int amount;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAmount() {
        return Integer.toString(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Luminosity(Date date, int amount) {
        this.date = date;
        this.amount = amount;
    }


}
