package es.unex.smartgreenadapt.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Notification {

    @SerializedName(value = "id")
    private int idNot;

    @SerializedName("date")
    private Date date;

    @SerializedName("amount")
    private String description;


    public int getIdNot() {
        return idNot;
    }

    public String getDate() {
        return date.toString();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Notification(Date date, String description) {
        this.date = date;
        this.description = description;
    }
}
