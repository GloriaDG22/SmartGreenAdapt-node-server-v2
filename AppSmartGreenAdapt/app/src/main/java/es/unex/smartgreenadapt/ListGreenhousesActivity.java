package es.unex.smartgreenadapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import es.unex.smartgreenadapt.databinding.ActivityListGreenhousesBinding;
import es.unex.smartgreenadapt.ui.login.LoginActivity;


public class ListGreenhousesActivity extends AppCompatActivity {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";

    private ActivityListGreenhousesBinding binding;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListGreenhousesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Parte superior de la pantalla
        toolbar = findViewById(R.id.toolbar_general);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("List greenhouses");

        Fragment fragment = new ButtonFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_buttons, fragment).commit();

        binding.fab.setOnClickListener(view ->
                //TODO Crear nuevo invernadero
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case (android.R.id.home):
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
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
        //TODO logout
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

        finish();
    }
}