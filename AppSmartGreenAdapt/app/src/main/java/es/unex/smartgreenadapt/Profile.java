package es.unex.smartgreenadapt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Profile extends AppCompatActivity {

    TextView mEmail, mPassword, mUsername;

    String email, password, username = null;
    private Toolbar toolbar;

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
