package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.login.MessageUser;
import es.unex.smartgreenadapt.model.login.User;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGreenhouse extends AppCompatActivity {

    public static final String EXTRA_EDIT_GREENHOUSE = "EDIT_GREENHOUSE";
    EditText mName, mIdUser;
    TextView mUsername;
    Button mSave;

    private MessageGreenhouse mGreenhouse;

    private Toolbar toolbar;

    InformationNetworkLoaderRunnable mInformNet;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_greenhouse);

        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = findViewById(R.id.value_name);
        mIdUser = findViewById(R.id.value_id_user);
        mSave = findViewById(R.id.save);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mUsername = findViewById(R.id.value_username);
            mUsername.setVisibility(View.VISIBLE);
            findViewById(R.id.text_username).setVisibility(View.INVISIBLE);
            mGreenhouse = (MessageGreenhouse) bundle.getSerializable(EditGreenhouse.EXTRA_EDIT_GREENHOUSE);
        }

        mInformNet = InformationNetworkLoaderRunnable.getInstance();
        if(mGreenhouse != null) {
            getSupportActionBar().setTitle(R.string.action_edit);
            mName.setText(mGreenhouse.getName());
            mIdUser.setInputType(InputType.TYPE_CLASS_NUMBER);
            mIdUser.setText(mGreenhouse.getIdUsername()+"");

            Call<User> register = mInformNet.getApi().users();
            register.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    for (MessageUser user:response.body().getList()) {
                        if(user.getId() == mGreenhouse.getIdUsername())
                            mUsername.setText(user.getUsername());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

            mSave.setOnClickListener(menuItem -> {
                onClick(menuItem);
                onBackPressed();
                onRestart();
            });

        }else{
            getSupportActionBar().setTitle(R.string.action_add);

            mSave.setText("Next");
            mSave.setOnClickListener(menuItem -> {
                onClick(menuItem);

                //TODO Hacer aquí la creación del nuevo fragment

            });
        }
    }

    private void onClick(View menuItem) {
        if(mGreenhouse != null) {
            MessageGreenhouse newGH = new MessageGreenhouse(mGreenhouse.getId(), mName.getText().toString(), Integer.parseInt(mIdUser.getText().toString()),0);
            mInformNet = InformationNetworkLoaderRunnable.getInstance();

            Call<MessageResponse> responseCall = mInformNet.getApi().putGreenhouse(newGH);

            responseCall.enqueue(new Callback<MessageResponse>() {
                @SuppressLint("SimpleDateFormat")
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()) {
                        MessageResponse mgResponse = response.body();
                        if (mgResponse.getAffectedRows() == 1)
                            Log.println(Log.INFO, "Result", "The greenhouse has been edited.");
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {

                }
            });
        }else{
            MessageGreenhouse newGH = new MessageGreenhouse(0, mName.getText().toString(), Integer.parseInt(mIdUser.getText().toString()),0);
            Call<MessageResponse> register = mInformNet.getApi().postGreenhouse(newGH);
            register.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                    if (response.isSuccessful()) {
                        MessageResponse mgResponse = response.body();
                        if (mgResponse.getAffectedRows() == 1)
                            Log.println(Log.INFO, "Result", "The greenhouse has been added.");
                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {

                }
            });
        }
    }

    // Seleción del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
