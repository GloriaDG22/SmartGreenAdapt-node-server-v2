package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.databinding.FragmentButtonsGreenhouseBinding;
import es.unex.smartgreenadapt.model.greenhouse.Greenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;
import es.unex.smartgreenadapt.model.greenhouse.Notification;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtonFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private ListButtonAdapter listButtonAdapter;
    Greenhouse mListButtons = new Greenhouse();
    private int idUser;

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buttons_greenhouse, container, false);

        mRecyclerView = root.findViewById(R.id.buttonRV);

        listButtonAdapter = ListButtonAdapter.getInstance(inflater, this.getContext());

        Bundle bundle = getActivity().getIntent().getExtras();
        idUser = (int) bundle.getSerializable(LoginActivity.EXTRA_USER);

/*
        mListButtons.add(new Greenhouse(1,"Greenhouse 1",1));
        mListButtons.add("Greenhouse 2");
        mListButtons.add("Greenhouse 3");
        mListButtons.add("Greenhouse 4");*/

        getGreenhouses();

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        listButtonAdapter.allListButtons(mListButtons.getList());

        mRecyclerView.setAdapter(listButtonAdapter);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void getGreenhouses(){
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Call<Greenhouse> greenhouseCall = mInformNet.getApi().getGreenhouses(idUser);

        greenhouseCall.enqueue(new Callback<Greenhouse>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<Greenhouse> call, Response<Greenhouse> response) {
                if (response.isSuccessful()) {
                    Greenhouse responseGreen = response.body();
                    mListButtons.setList(responseGreen.getList());

                    onResume();
                }
            }

            @Override
            public void onFailure (Call <Greenhouse> call, Throwable t){

            }

        });

    }
}