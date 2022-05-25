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
import es.unex.smartgreenadapt.model.login.MessageUser;
import es.unex.smartgreenadapt.model.login.User;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButtonFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private ListButtonAdapter listButtonAdapter;
    Greenhouse mListButtons = new Greenhouse();
    private MessageUser mUser;
    private User listUsers = new User();

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_buttons_greenhouse, container, false);

        mRecyclerView = root.findViewById(R.id.buttonRV);

        listButtonAdapter = ListButtonAdapter.getInstance(inflater, this.getContext());

        Bundle bundle = getActivity().getIntent().getExtras();
        mUser = (MessageUser) bundle.getSerializable(LoginActivity.EXTRA_USER);

        return root;
    }

    @Override
    public void onStart(){
        super.onStart();

        mListButtons.getList().clear();
        getGreenhouses();
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

        if(!mUser.getUsername().equals("admin")) {
            listUsers.getList().clear();
            listUsers.addMessage(new MessageUser(mUser.getId(), mUser.email, mUser.username, mUser.password));

            callGreenhouse();

        }else {
            Call<User> register = mInformNet.getApi().users();
            register.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    listUsers = response.body();

                    callGreenhouse();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    public void callGreenhouse(){
        Call<Greenhouse> greenhouseCall;
        for (MessageUser user: listUsers.getList()) {
            greenhouseCall = mInformNet.getApi().getGreenhouses(user.getId());

            greenhouseCall.enqueue(new Callback<Greenhouse>() {
                @Override
                public void onResponse(Call<Greenhouse> call, Response<Greenhouse> response) {
                    if (response.isSuccessful()) {
                        Greenhouse responseGreen = response.body();
                        mListButtons.addList(responseGreen.getList());

                        onResume();
                    }
                }

                @Override
                public void onFailure(Call<Greenhouse> call, Throwable t) {

                }

            });
        }
    }
}