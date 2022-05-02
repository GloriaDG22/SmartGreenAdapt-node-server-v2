package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

public class HeatingMessage {
    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("idGreenhouse")
    private int idGreenhouse;

    @SerializedName("value")
    private int value;

    @SerializedName("affects")
    private String affects;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdGreenhouse() {
        return idGreenhouse;
    }

    public void setIdGreenhouse(int idGreenhouse) {
        this.idGreenhouse = idGreenhouse;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getAffects() {
        return affects;
    }

    public void setAffects(String affects) {
        this.affects = affects;
    }

    public HeatingMessage(int id, String type, int idGreenhouse, int value, String affects) {
        this.id = id;
        this.type = type;
        this.idGreenhouse = idGreenhouse;
        this.value = value;
        this.affects = affects;
    }
}
