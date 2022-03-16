package es.unex.smartgreenadapt.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.sql.Date;

import es.unex.smartgreenadapt.model.Notification;

@Dao
public interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Notification notification);

    @Query("SELECT * FROM notification")
    LiveData<List<Notification>> getAll();

    @Query("SELECT * FROM notification WHERE notification.date LIKE :date ")
    LiveData<List<Notification>> getByDate(Date date);

    @Query("DELETE FROM notification")
    void deleteAll();
}
