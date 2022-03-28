package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.MessageNotification;

public class NotificationDetailActivity extends AppCompatActivity implements ListNotificationAdapter.OnNotListener{


    private TextView name, date, amount, type;
    private MessageNotification mNotification;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_notification_detail);

        setSupportActionBar(findViewById(R.id.toolbar_general));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail notification");

        Bundle bundle = getIntent().getExtras();
        mNotification = (MessageNotification) bundle.getSerializable(NotificationsFragment.EXTRA_NOTIFICATION);

        name = findViewById(R.id.text_notification);
        date = findViewById(R.id.value_date);
        amount = findViewById(R.id.value_amount);
        type = findViewById(R.id.value_type);

        name.setText("Notification " + mNotification.getIdNot());
        try {
            date.setText(mNotification.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        amount.setText((mNotification.getDescription()));

        String stringType = null;
        if (mNotification.isWarning()) stringType = "Warning";
        else stringType = "Error";
        type.setText(stringType);
    }

        @Override
    public void onNotificationClick(int position) {

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
