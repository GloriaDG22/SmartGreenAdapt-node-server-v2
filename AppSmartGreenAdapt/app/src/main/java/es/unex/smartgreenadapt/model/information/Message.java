package es.unex.smartgreenadapt.model.information;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    public int idTem;
    @SerializedName("amount")
    public float amount;
    @SerializedName("date")
    public String date;
}
