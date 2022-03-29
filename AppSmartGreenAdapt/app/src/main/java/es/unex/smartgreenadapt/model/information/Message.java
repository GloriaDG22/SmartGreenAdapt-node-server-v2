package es.unex.smartgreenadapt.model.information;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    public int id;
    @SerializedName("amount")
    public float amount;
    @SerializedName("date")
    public String date;
    @SerializedName("min_value")
    public float min_value;
    @SerializedName("max_value")
    public float max_value;
}
