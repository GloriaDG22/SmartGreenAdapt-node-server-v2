package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import es.unex.smartgreenadapt.db.DBConn;
import es.unex.smartgreenadapt.ui.information.InformationFragment;
import es.unex.smartgreenadapt.ui.notifications.ListNotificationAdapter;
import es.unex.smartgreenadapt.ui.notifications.NotificationsFragment;
import es.unex.smartgreenadapt.ui.state.StateFragment;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomBar;

    DBConn mDBConn;

    private ListNotificationAdapter listInformationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBConn = DBConn.getInstance();

        // Parte superior de la pantalla
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        View logoView = toolbar.getChildAt(0);
        logoView.setOnClickListener(v -> {
            cargarFragment(new InformationFragment());
            bottomBar.setSelectedItemId(R.id.navigation_information);
        });

        // Parte inferior de la pantalla
        bottomBar = findViewById(R.id.nav_view);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_information:
                        fragment = new InformationFragment();
                        break;

                    case R.id.navigation_state:
                        fragment = new StateFragment();
                        break;

                    case R.id.navigation_notifications:
                        fragment = new NotificationsFragment();
                        break;
                }
                return cargarFragment(fragment);
            }
        });
        bottomBar.setSelectedItemId(R.id.navigation_information);
    }

    // Carga los fragment del main activity
    private boolean cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
        return true;
    }
}