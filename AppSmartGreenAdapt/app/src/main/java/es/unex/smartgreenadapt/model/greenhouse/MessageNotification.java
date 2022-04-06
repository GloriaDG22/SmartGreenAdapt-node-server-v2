package es.unex.smartgreenadapt.model.greenhouse;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MessageNotification implements Serializable {

    @SerializedName("id")
    private int idNot;
    @SerializedName("date")
    private String date;
    private String description;
    @SerializedName("is_warning")
    private int isWarning;
    @SerializedName("problem")
    private String problem;
    @SerializedName("status")
    private String status;
    @SerializedName("idGreenhouse")
    public int idGreenhouse;

    public int getIdNot() {
        return idNot;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDate() throws ParseException {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWarning() {
        return isWarning == 0;
    }

    public String getProblem() {
        return problem;
    }

    public String getStatus() {
        return status;
    }

    public int getIdGreenhouse() {
        return idGreenhouse;
    }

    @SuppressLint("SimpleDateFormat")
    public MessageNotification(String date, int isWarning, String problem, String status) throws ParseException {
        this.date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(date.replaceAll("Z$", "+0000")).toString();
        this.description = problem + " " + status;
        this.isWarning = isWarning;
        this.problem = problem;
        this.status = status;
    }
}
