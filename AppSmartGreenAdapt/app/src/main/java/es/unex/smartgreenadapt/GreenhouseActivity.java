package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.login.MessageUser;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.ui.information.InformationFragment;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import es.unex.smartgreenadapt.ui.notifications.NotificationsFragment;
import es.unex.smartgreenadapt.ui.state.StateFragment;
import es.unex.smartgreenadapt.ui.state.StateHeatingDetail;
import es.unex.smartgreenadapt.ui.state.StateIrrigationSprinklersDetail;
import es.unex.smartgreenadapt.ui.state.StateWindowDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GreenhouseActivity extends AppCompatActivity {
    public static final String EXTRA_GREENHOUSE = "GREENHOUSE";
    Toolbar toolbar;
    BottomNavigationView bottomBar;
    MessageGreenhouse mGreenhouse;

    String username = null;
    SharedPreferences preferences;

    InformationFragment informationFragment = new InformationFragment();
    StateFragment stateFragment = new StateFragment();
    NotificationsFragment notificationFragment = new NotificationsFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Parte superior de la pantalla
        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View logoView = toolbar.getChildAt(0);
        logoView.setOnClickListener(v -> {
            cargarFragment(new InformationFragment());
            bottomBar.setSelectedItemId(R.id.navigation_information);
        });

        preferences = getSharedPreferences("DatesUser", MODE_PRIVATE);
        username = preferences.getString("Username", "Username");

        Bundle bundle = getIntent().getExtras();
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable(GreenhouseActivity.EXTRA_GREENHOUSE);

        // Parte inferior de la pantalla
        bottomBar = findViewById(R.id.nav_view);
        bottomBar.setOnItemSelectedListener(menuItem -> {
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.navigation_information:
                    fragment = informationFragment;
                    break;

                case R.id.navigation_state:
                    fragment = stateFragment;
                    break;

                case R.id.navigation_notifications:
                    fragment = notificationFragment;
                    break;
            }
            return cargarFragment(fragment);
        });
        bottomBar.setSelectedItemId(R.id.navigation_information);
    }

    // Carga los fragment del main activity
    private boolean cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_edit_greenhouse);
        item.setVisible(username.equals("admin"));
        item = menu.findItem(R.id.action_delete_greenhouse);
        item.setVisible(username.equals("admin"));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case (R.id.action_profile):
                executeProfile();
                return false;
            case (R.id.action_logout):
                executeLogout();
                return true;
            case (R.id.action_about):
                executeAbout();
                return true;
            case (R.id.action_edit_greenhouse):
                executeEdit();
                return true;
            case (R.id.action_delete_greenhouse):
                executeDelete(item);
                return true;
            case (android.R.id.home):
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    // Activity edit greenhouse
    public void executeEdit(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(EditGreenhouse.EXTRA_EDIT_GREENHOUSE, mGreenhouse);

        Intent i = new Intent(this, EditGreenhouse.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    // Activity delete greenhouse
    public void executeDelete(MenuItem item){
        InformationNetworkLoaderRunnable informNet = InformationNetworkLoaderRunnable.getInstance();

        Call<MessageResponse> responseCall = informNet.getApi().deleteGreenhouse(mGreenhouse.getId());

        responseCall.enqueue(new Callback<MessageResponse>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful()) {
                    MessageResponse mgResponse = response.body();
                    if (mgResponse.getAffectedRows() == 1)
                        Log.println(Log.INFO, "Result", "The greenhouse has been deleted.");
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
        finish();
    }

    // Activity setting
    public void executeAbout(){
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

    // Activity profile
    public void executeProfile(){
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
    }

    //Activity login
    private void executeLogout() {
        preferences.edit().clear().apply();

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

        finish();
        finish();
    }

    public void onClickDetalleState(ActuatorAllData mActuatorData, MessageGreenhouse mGreenhouse){
        Bundle args = new Bundle();
        args.putSerializable("STATE", mActuatorData);
        args.putSerializable("GREENHOUSE", mGreenhouse);

        Fragment toFragment;
        if("Window".equals(mActuatorData.getClassType())){
            toFragment = new StateWindowDetail();
        } else if("Heating".equals(mActuatorData.getClassType())){
            toFragment = new StateHeatingDetail();
        } else if("Sprinklers".equals(mActuatorData.getClassType())){
            toFragment = new StateIrrigationSprinklersDetail();
        } else {
            toFragment = new StateIrrigationSprinklersDetail();
        }

        toFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, toFragment)
                .addToBackStack(null)
                .commit();
    }

    public void volverAlListado(MessageGreenhouse mGreenhouse){
        Bundle args = new Bundle();
        args.putSerializable(GreenhouseActivity.EXTRA_GREENHOUSE, mGreenhouse);

        Fragment toFragment = new StateFragment();
        toFragment.setArguments(args);
        getSupportFragmentManager()
                .popBackStack();
    }
}