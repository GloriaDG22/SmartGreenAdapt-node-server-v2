package es.unex.smartgreenadapt.model.greenhouse.information;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    public int id;
    @SerializedName("amount")
    public float amount;
    @SerializedName("date")
    public String date;
    @SerializedName("idGreenhouse")
    public int idGreenhouse;
}
