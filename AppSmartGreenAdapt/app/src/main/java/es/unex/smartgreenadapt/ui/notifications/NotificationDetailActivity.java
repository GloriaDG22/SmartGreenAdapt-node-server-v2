package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Locale;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;

public class NotificationDetailActivity extends AppCompatActivity implements ListNotificationAdapter.OnNotListener{


    private TextView name, date, description, type;
    private MessageNotification mNotification;
    private ListInformation mListInfo;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);

        mListInfo = ListInformation.getInstance();

        setContentView(R.layout.activity_notification_detail);

        setSupportActionBar(findViewById(R.id.toolbar_general));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail notification");

        Bundle bundle = getIntent().getExtras();
        mNotification = (MessageNotification) bundle.getSerializable(NotificationsFragment.EXTRA_NOTIFICATION);

        name = findViewById(R.id.text_notification);
        date = findViewById(R.id.value_date);
        description = findViewById(R.id.value_description);
        type = findViewById(R.id.value_type);

        name.setText(mNotification.getDescription());
        try {
            date.setText(mNotification.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(mNotification.isWarning()) {
            int pos = mListInfo.getPositionTitle(mNotification.getProblem());
            description.setText("The " + mNotification.getProblem().toLowerCase(Locale.ROOT) + " value should be between " + mListInfo.getValueMin(pos) + " and " + mListInfo.getValueMax(pos) + ".");
        }else
            description.setText("The value is wrong. You need to contact the support team.");

        String stringType;
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
