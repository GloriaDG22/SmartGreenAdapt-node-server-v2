package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.util.ArrayList;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.db.DBConn;
import es.unex.smartgreenadapt.model.Notification;

public class NotificationsFragment extends Fragment implements ListNotificationAdapter.OnNotListener {

    private ListNotificationAdapter listNotificationAdapter;
    private RecyclerView mRecyclerView;

    private TextView textNoHayNotificaciones;

    ArrayList <Notification> listNot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        textNoHayNotificaciones = root.findViewById(R.id.text_noNotif);

        mRecyclerView = root.findViewById(R.id.notificationRV);


        listNot = new ArrayList<>();
        listNot.add(new Notification(Date.valueOf("2022-02-22"), "Demasiada humedad"));
        listNot.add(new Notification(Date.valueOf("2022-02-21"), "Temperatura demasiado baja"));

        listNotificationAdapter = new ListNotificationAdapter(inflater, this.getContext());

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        listNotificationAdapter.allListNotifications(listNot);

        if(listNot.isEmpty()){
            textNoHayNotificaciones.setVisibility(View.VISIBLE);
        }

        mRecyclerView.setAdapter(listNotificationAdapter);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onNotClick(int position) {
        // Funcion que no se implementa
    }


}