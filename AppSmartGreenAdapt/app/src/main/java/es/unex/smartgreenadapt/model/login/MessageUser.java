package es.unex.smartgreenadapt.model.login;

import com.google.gson.annotations.SerializedName;

public class MessageUser {

    @SerializedName("id")
    public int id;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("email")
    public String email;

    public MessageUser(String email, String username, String password) {
        this.email = email;

        if(username==null)
            this.username = email.substring(0, email.indexOf("@"));
        else
            this.username = username;

        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isEqualPassword(String password){
        return this.password.equals(password);
    }


}
