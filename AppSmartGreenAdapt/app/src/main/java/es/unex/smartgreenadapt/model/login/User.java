package es.unex.smartgreenadapt.model.login;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;

public class User {

        @SerializedName("message")
        public ArrayList<MessageUser> message = new ArrayList<>();

        public ArrayList<MessageUser> getList() {
            return message;
        }

        public void setList(ArrayList<MessageUser> message) {
            this.message = message;
        }

        public void addMessage(MessageUser message){ this.message.add(message);}



}
