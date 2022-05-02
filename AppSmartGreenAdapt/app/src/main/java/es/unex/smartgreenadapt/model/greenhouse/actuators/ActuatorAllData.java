package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActuatorAllData implements Serializable {
    private int id;

    private String classType;

    private int idGreenhouse;

    private int isOn;

    private String affects;

    private String heatingType;

    private int value;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    public String getHeatingType() {
        return heatingType;
    }

    public void setHeatingType(String heatingType) {
        this.heatingType = heatingType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ActuatorAllData(int id, String classType, int idGreenhouse, int isOn, String affects, String heatingType, int value, String name) {
        this.id = id;
        this.classType = classType;
        this.idGreenhouse = idGreenhouse;
        this.isOn = isOn;
        this.affects = affects;
        this.heatingType = heatingType;
        this.value = value;
        this.name = name;
    }

    public ActuatorAllData() {
    }

    @Override
    public String toString() {
        return "ActuatorAllData{" +
                "id=" + id +
                ", classType='" + classType + '\'' +
                ", idGreenhouse=" + idGreenhouse +
                ", isOn='" + isOn + '\'' +
                ", affects='" + affects + '\'' +
                ", heatingType='" + heatingType + '\'' +
                ", value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
