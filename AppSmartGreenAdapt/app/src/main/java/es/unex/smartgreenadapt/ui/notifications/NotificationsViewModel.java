package es.unex.smartgreenadapt.ui.notifications;

import androidx.lifecycle.ViewModel;

import es.unex.smartgreenadapt.data.repository.NotificationRepository;

public class NotificationsViewModel extends ViewModel {

    private final NotificationRepository mNotificatonRepository;

    public NotificationsViewModel(NotificationRepository repository) {
        mNotificatonRepository = repository;
    }


    public String getNotifications() {
        return "SELECT * FROM notification";
    }
}