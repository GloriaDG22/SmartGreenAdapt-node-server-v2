package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WindowMessage implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("idGreenhouse")
    private int idGreenhouse;

    @SerializedName("is_on")
    private int isOn;

    @SerializedName("affects")
    private String affects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdGreenhouse() {
        return idGreenhouse;
    }

    public void setIdGreenhouse(int idGreenhouse) {
        this.idGreenhouse = idGreenhouse;
    }

    public int getIsOn() {
        return isOn;
    }

    public void setIsOn(int isOn) {
        this.isOn = isOn;
    }

    public String getAffects() {
        return affects;
    }

    public void setAffects(String affects) {
        this.affects = affects;
    }

    public WindowMessage(int id, String name, int idGreenhouse, int isOn, String affects) {
        this.id = id;
        this.name = name;
        this.idGreenhouse = idGreenhouse;
        this.isOn = isOn;
        this.affects = affects;
    }
}
