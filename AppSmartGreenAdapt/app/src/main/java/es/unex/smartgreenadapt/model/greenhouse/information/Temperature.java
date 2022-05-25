package es.unex.smartgreenadapt.model.greenhouse.information;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;


public class Temperature {
    @SerializedName("message")
    public ArrayList<Message> message = new ArrayList<>();

    private ArrayList<String> listStrings = new ArrayList<>();
    private ArrayList<Integer> listInts = new ArrayList<>();

    public int getIdTem() {
        return message.get(0).id;
    }

    public String getDate() {
        return message.get(0).date;
    }

    public String getAmount() {
        return message == null ? "-" : Float.toString(message.get(0).amount);
    }

    public void setLists(){
        listStrings.addAll(Arrays.asList("Heating","Sprinklers","Windows"));
        listInts.addAll(Arrays.asList(1,0,0));
    }

    public int getIdGreenhouse() {
        return message.get(0).idGreenhouse;
    }

    public ArrayList<String> getListStrings() {
        return listStrings;
    }

    public ArrayList<Integer> getListInts() {
        return listInts;
    }

}
