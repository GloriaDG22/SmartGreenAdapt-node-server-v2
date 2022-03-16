package es.unex.smartgreenadapt.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformationNetworkLoaderRunnable {

    private static InformationNetworkLoaderRunnable mInstance;
    private Retrofit mRetrofit;
    private String URLBase = "http://127.0.0.1:8080/";

    public InformationNetworkLoaderRunnable(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized InformationNetworkLoaderRunnable getInstance(){
        if(mInstance == null){
            mInstance = new InformationNetworkLoaderRunnable();
        }
        return mInstance;
    }

    public APIService getApi(){
        return mRetrofit.create(APIService.class);
    }
}

