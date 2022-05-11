package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.ui.information.InformationFragment;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import es.unex.smartgreenadapt.ui.notifications.NotificationsFragment;
import es.unex.smartgreenadapt.ui.state.StateFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    TextView mEmail, mPassword, mUsername;
    Button mDeleteAccount;

    String email, password, username = null;
    private Toolbar toolbar;

    InformationNetworkLoaderRunnable mInformNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.action_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsername = findViewById(R.id.value_username);
        mEmail = findViewById(R.id.value_email);
        mPassword = findViewById(R.id.value_password);

        SharedPreferences preferences = getSharedPreferences("DatesUser", MODE_PRIVATE);

        username = preferences.getString("Username", "Username");
        email = preferences.getString("Email", "Email");
        password = preferences.getString("Password", "Password");

        mUsername.setHint(username);
        mEmail.setHint(email);
        mPassword.setHint(password);

        mDeleteAccount = findViewById(R.id.delete_account);
        mDeleteAccount.setOnClickListener(menuItem -> {
            onClick(menuItem);

            Snackbar.make(menuItem, "The account has been deleted.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            preferences.edit().clear().apply();

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

            finish();

        });
    }

    private void onClick(View menuItem) {
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Call<MessageResponse> responseCall = mInformNet.getApi().deleteUser(email);

        responseCall.enqueue(new Callback<MessageResponse>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse mgResponse = response.body();
                    if (mgResponse.getAffectedRows() == 1)
                        Log.println(Log.INFO, "Result", "The account has been deleted.");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });

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
