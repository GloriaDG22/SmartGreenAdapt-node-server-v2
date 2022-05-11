package es.unex.smartgreenadapt.model.greenhouse.actuators;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ActuatorMessage implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("idGreenhouse")
    private int idGreenhouse;

    @SerializedName("is_on")
    private int isOn;

    @SerializedName("affects")
    private String affects;

    private ArrayList<String> listStrings = new ArrayList<>();
    private ArrayList<Integer> listInts = new ArrayList<>(Arrays.asList(1,0));


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<String> getListStrings() {
        return listStrings;
    }

    public ArrayList<Integer> getListInts() {
        return listInts;
    }

    public String getListStrings(int pos) {
        return listStrings.get(pos);
    }

    public Integer getListInts(int pos) {
        return listInts.get(pos);
    }

    public ActuatorMessage(int id, int idGreenhouse, int isOn, String affects) {
        this.id = id;
        this.idGreenhouse = idGreenhouse;
        this.isOn = isOn;
        this.affects = affects;
        String s;
        ArrayList<String> listStrings = new ArrayList<>();

        int ini = 0;
        int fin = 0;
        System.out.println(affects.length()-1 + " " + fin);

        fin = affects.indexOf(",", ini+1);
        while(fin!=-1) {
            s = affects.substring(ini, fin);
            ini=fin+1;
            listStrings.add(s);

            fin = affects.indexOf(",", ini+1);
        }

        for(String a : listStrings){
            System.out.println(a + " ");
        }
    }

}
