package es.unex.smartgreenadapt.ui.state;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.ListGreenhousesActivity;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import es.unex.smartgreenadapt.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateWindowDetail extends AppCompatActivity {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";
    private ActuatorAllData windowData;
    private MessageGreenhouse mGreenhouse;
    private TextView titulo;
    private ImageView imagen;
    private Switch switchAbierto;
    private CheckBox temp, calidad, lum, hum;
    private EditText nombre;

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.println(" Entra en el detalle de ventana ");
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_window_detail);
        windowData = (ActuatorAllData) bundle.getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable("GREENHOUSE");

        titulo = findViewById(R.id.textViewTituloWD);
        imagen = findViewById(R.id.imageViewWD);
        switchAbierto = findViewById(R.id.isOnSwitchWD);
        temp = findViewById(R.id.checkBoxWD);
        calidad = findViewById(R.id.checkBox2WD);
        lum = findViewById(R.id.checkBox3WD);
        hum = findViewById(R.id.checkBox4WD);
        nombre = findViewById(R.id.windowNameEditText);

        // seteo de datos
        titulo.setText("Ventana");
        imagen.setImageResource(R.drawable.ic_window_svgrepo_com);
        if(windowData.getIsOn() == 1){
            // 1 - LA VENTANA ESTÁ ABIERTA
            switchAbierto.setChecked(Boolean.TRUE);
        } else {
            switchAbierto.setChecked(Boolean.FALSE);
        }

        if(windowData.getAffects().contains("Temperature")){
            temp.setChecked(Boolean.TRUE);
        } else {
            temp.setChecked(Boolean.FALSE);
        }
        if(windowData.getAffects().contains("Luminosity")){
            lum.setChecked(Boolean.TRUE);
        } else {
            lum.setChecked(Boolean.FALSE);
        }
        if(windowData.getAffects().contains("Air Quality")){
            calidad.setChecked(Boolean.TRUE);
        } else {
            calidad.setChecked(Boolean.FALSE);
        }
        if(windowData.getAffects().contains("Humidity")){
            hum.setChecked(Boolean.TRUE);
        } else {
            hum.setChecked(Boolean.FALSE);
        }

        nombre.setText(windowData.getName());

        Button cancelar = findViewById(R.id.buttonWD);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getApplicationContext(), "Cambios cancelados", Toast.LENGTH_SHORT);
               Intent intent = new Intent(StateWindowDetail.this, StateFragment.class);
               Bundle bundle = new Bundle();
               bundle.putSerializable(EXTRA_GREENHOUSE, windowData.getIdGreenhouse());
               intent.putExtras(bundle);
               finish();
           }
        });
        Button guardar = findViewById(R.id.button2WD);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String affects  = "";
                if(temp.isChecked()){
                    affects = affects + "Temperature,";
                }
                if(lum.isChecked()){
                    affects = affects + "Luminosity,";
                }
                if(calidad.isChecked()){
                    affects = affects + "Air Quality,";
                }
                if(hum.isChecked()){
                    affects = affects + "Humidity,";
                }

                String nameW = nombre.getText().toString();
                int ison = 0;
                if(switchAbierto.isChecked()){
                    ison = 1;
                }

                WindowMessage windowToSave = new WindowMessage(windowData.getId(), nameW, windowData.getIdGreenhouse(), ison, affects);

                Call<MessageResponse> login = mInformNet.getApi().putWindow(windowToSave);

                login.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse mgResponse = response.body();
                        if(mgResponse.getAffectedRows() == 1) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable(GreenhouseActivity.EXTRA_GREENHOUSE, mGreenhouse);
                            Intent intent = new Intent(StateWindowDetail.this, GreenhouseActivity.class);
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
        });
    }
}
