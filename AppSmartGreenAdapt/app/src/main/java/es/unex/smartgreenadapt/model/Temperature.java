package es.unex.smartgreenadapt.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Temperature {

    @SerializedName(value = "id")
    private int idTem;

    @SerializedName("date")
    private Date date;

    @SerializedName("amount")
    private int amount;


    public int getIdTem() {
        return idTem;
    }

    public String getDate() {
        return date.toString();
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

    public Temperature(Date date, int amount) {
        this.date = date;
        this.amount = amount;
    }


}
