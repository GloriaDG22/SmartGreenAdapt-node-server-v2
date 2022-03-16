package es.unex.smartgreenadapt.data.repository;


import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.data.local.NotificationDao;
import es.unex.smartgreenadapt.model.Notification;

public class NotificationRepository {

    private static NotificationRepository mInstance;

    private final NotificationDao mNotificationDao;

    private static synchronized NotificationRepository getInstance(NotificationDao notificationDao){
        if(mInstance == null){
            mInstance = new NotificationRepository(notificationDao);
        }
        return mInstance;
    }

    private  NotificationRepository(NotificationDao notificationDao){
        mNotificationDao = notificationDao;
    }

    public LiveData<List<Notification>> getNotifications() {
        return mNotificationDao.getAll();
    }
}
