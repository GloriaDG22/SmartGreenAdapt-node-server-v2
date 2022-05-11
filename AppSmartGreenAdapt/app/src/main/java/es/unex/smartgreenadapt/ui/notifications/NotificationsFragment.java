package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;
import es.unex.smartgreenadapt.model.greenhouse.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment implements ListNotificationAdapter.OnNotListener {

    public static final String EXTRA_NOTIFICATION = "NOTIFICATION";
    private ListNotificationAdapter listNotificationAdapter;
    private RecyclerView mRecyclerView;

    private TextView textNoHayNotificaciones;

    Notification mListNotifications = new Notification();

    MessageNotification mNotification;
    MessageGreenhouse mGreenhouse;
    Context mContext;

    private InformationNetworkLoaderRunnable mInformNet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        textNoHayNotificaciones = root.findViewById(R.id.text_noNotif);

        mRecyclerView = root.findViewById(R.id.notificationRV);

        listNotificationAdapter = ListNotificationAdapter.getInstance(inflater, this.getContext(), this);

        Bundle bundle = getActivity().getIntent().getExtras();
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable(GreenhouseActivity.EXTRA_GREENHOUSE);

        mContext = getContext();
        getNotifications();

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();

        listNotificationAdapter.allListNotifications(mListNotifications.getList());

        if(mListNotifications.getList().isEmpty())
            textNoHayNotificaciones.setVisibility(View.VISIBLE);
        else
            textNoHayNotificaciones.setVisibility(View.INVISIBLE);

        mRecyclerView.setAdapter(listNotificationAdapter);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onNotificationClick(int position) {
        mNotification = mListNotifications.getList().get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_NOTIFICATION, mNotification);

        Intent intent = new Intent(mContext, NotificationDetailActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public void getNotifications(){
        mListNotifications.getList().clear();
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Call<Notification> notifications = mInformNet.getApi().getNotifications(mGreenhouse.getId());

        notifications.enqueue(new Callback<Notification>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if (response.isSuccessful()) {
                    Notification responseNotification = response.body();
                    for (int i = 0; i<responseNotification.getList().size(); i++ ){
                        MessageNotification mn;
                        mn = responseNotification.getList().get(i);

                        //Set Date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                        Date d = null;
                        try {
                            d = sdf.parse(mn.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedTime = output.format(d);
                        mn.setDate(formattedTime);

                        //set Description
                        String desc = mn.getProblem()+ " " + mn.getStatus();
                        mn.setDescription(desc);

                    }
                    mListNotifications.setList(responseNotification.getList());

                    onResume();
                }
            }

                @Override
                public void onFailure (Call <Notification> call, Throwable t){

                }

        });

    }
}