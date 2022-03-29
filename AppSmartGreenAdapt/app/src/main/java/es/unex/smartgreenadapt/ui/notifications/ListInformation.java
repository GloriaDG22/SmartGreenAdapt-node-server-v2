package es.unex.smartgreenadapt.ui.notifications;

import es.unex.smartgreenadapt.R;

public class ListInformation {
    private static ListInformation mInstance;

    // Lista de id de los recursos
    private final int [] mList = new int[]{R.string.text_temperature, R.string.text_luminosity, R.string.text_airquality, R.string.text_humidity};

    // List of min values
    private final int [] mListMin = new int[]{8, 0, 0, 50};
    // List of max values
    private final int [] mListMax = new int[]{35, 1000, 50, 90};

    //Lista de String
    private final String [] mListString = new String[]{"Temperature", "Luminosity", "AirQuality", "Humidity"};

    public static synchronized ListInformation getInstance(){
        if(mInstance == null){
            mInstance = new ListInformation();
        }
        return mInstance;
    }

    //Methods INT
    public int getItemLista(int posicion){
        return mList[posicion];
    }

    public int[] getAll(){
        return mList;
    }

    //Methods MIN
    public int getValueMin(int pos){ return mListMin[pos];}

    //Methods MAX
    public int getValueMax(int pos){ return mListMax[pos];}

    //Methods STRING
    public String[] getAllString(){
        return mListString;
    }

    public int getPositionTitle(String title){
        for(int i = 0; i< mListString.length; i++){
            if(mListString[i].equals(title))
                return i;
        }
        return -1;
    }
}
