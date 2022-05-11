package es.unex.smartgreenadapt.model.greenhouse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageGreenhouse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("idUsuario")
    private int idUsername;

    @SerializedName("idInformation")
    private int idInformation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getIdUsername() {
        return idUsername;
    }

    public int getIdInformation() {
        return idInformation;
    }

    public MessageGreenhouse(int id, String name, int idUsername, int idInformation) {
        this.id = id;
        this.name = name;
        this.idUsername = idUsername;
        this.idInformation = idInformation;
    }
}
