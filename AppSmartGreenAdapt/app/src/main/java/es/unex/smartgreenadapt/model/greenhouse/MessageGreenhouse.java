package es.unex.smartgreenadapt.model.greenhouse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageGreenhouse implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("idUsername")
    private int idUsername;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIdUsername() {
        return idUsername;
    }

    public MessageGreenhouse(int id, String name, int idUsername) {
        this.id = id;
        this.name = name;
        this.idUsername = idUsername;
    }
}
