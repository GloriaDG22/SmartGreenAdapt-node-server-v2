package es.unex.smartgreenadapt.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.ListGreenhousesActivity;
import es.unex.smartgreenadapt.Profile;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.login.MessageUser;
import es.unex.smartgreenadapt.model.login.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Patterns;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String EXTRA_USER = "USER";
    private EditText editTextEmail;
    private EditText editTextPassword;

    private InformationNetworkLoaderRunnable mInformNet;

    private User mUsers = new User();

    String mEmail, mPassword, mUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.value_email);
        editTextPassword = findViewById(R.id.value_password);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        mInformNet = InformationNetworkLoaderRunnable.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Call<User> register = mInformNet.getApi().users();
        register.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mUsers = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mEmail != null) {
            Intent intent = new Intent(this, Profile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
}

    private boolean isValidCredential() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return false;
        }

        return true;
    }

    //Validate user
    private void userLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        for(int i = 0; i<mUsers.getList().size(); i++) {
            MessageUser userAux = mUsers.getList().get(i);
            if (userAux.getEmail().equals(email)) {
                if(userAux.isEqualPassword(password)) {
                    SharedPreferences preferences = getSharedPreferences("DatesUser", MODE_PRIVATE);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Username", userAux.getUsername());
                    editor.putString("Email", email);
                    editor.putString("Password", password);
                    editor.apply();

                    chargePreferences();
                    //onBackPressed();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.EXTRA_USER, userAux.getId());
                    Intent intent = new Intent(LoginActivity.this, ListGreenhousesActivity.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{
                    editTextPassword.setError("Password wrong");
                    editTextPassword.requestFocus();
                }

            }
        }
    }

    //Create user
    private void userRegister() {
        MessageUser newUser = new MessageUser(editTextEmail.getText().toString(), null, editTextPassword.getText().toString());
        Call<MessageResponse> login = mInformNet.getApi().postUser(newUser);

        Log.println(Log.ASSERT, "info", "Ha ejecutado en user: " + login.isExecuted());

        login.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                MessageResponse mgResponse = response.body();
                if(mgResponse.getAffectedRows() == 1) {

                    SharedPreferences preferences = getSharedPreferences("DatesUser", MODE_PRIVATE);

                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Username", newUser.getUsername());
                    editor.putString("Email", email);
                    editor.putString("Password", password);
                    editor.apply();

                    chargePreferences();


                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LoginActivity.EXTRA_USER, mgResponse.getInsertId());
                    Intent intent = new Intent(LoginActivity.this, ListGreenhousesActivity.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if(isValidCredential()) {
                    userLogin();
                    break;
                }
            case R.id.register:
                if(isValidCredential() & !isExistEmail())
                    userRegister();
                break;
        }
    }

    private boolean isExistEmail() {
        for(int i = 0; i<mUsers.getList().size(); i++) {
            MessageUser userAux = mUsers.getList().get(i);
            if (userAux.getEmail().equals(editTextEmail.getText().toString())) {
                editTextEmail.setError("This email isn't allowed");
                editTextEmail.requestFocus();
                return true;
            }
        }
        return false;
    }


    private void chargePreferences() {
        SharedPreferences preferences = getSharedPreferences("DatesUser", MODE_PRIVATE);

        mUsername = preferences.getString("Username", "Username");
        mEmail = preferences.getString("Email", "Email");
        mPassword = preferences.getString("Password", "Password");
    }
}