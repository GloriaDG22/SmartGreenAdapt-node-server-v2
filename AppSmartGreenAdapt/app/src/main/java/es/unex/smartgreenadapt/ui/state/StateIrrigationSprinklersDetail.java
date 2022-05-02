package es.unex.smartgreenadapt.ui.state;

import android.content.Intent;
import android.os.Bundle;
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
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateIrrigationSprinklersDetail extends AppCompatActivity {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";
    private ActuatorAllData actuatorData;
    private TextView titulo;
    private ImageView imagen;
    private Switch switchAbierto;
    private CheckBox temp, calidad, lum, hum;
    private MessageGreenhouse mGreenhouse;

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.println(" Entra en el detalle de riego y aspersores ");
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_sprinklers_irrigation_detail);
        actuatorData = (ActuatorAllData) bundle.getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable("GREENHOUSE");

        titulo = findViewById(R.id.textViewTituloSID);
        imagen = findViewById(R.id.imageViewSID);
        switchAbierto = findViewById(R.id.isOnSwitchSID);
        temp = findViewById(R.id.checkBoxSID);
        calidad = findViewById(R.id.checkBox2SID);
        lum = findViewById(R.id.checkBox3SID);
        hum = findViewById(R.id.checkBox4SID);

        // seteo de datos
        if(actuatorData.getClassType().equals("Sprinklers")){
            titulo.setText("Aspersores");
            imagen.setImageResource(R.drawable.ic_irrigation_svgrepo_com);
        } else {
            titulo.setText("Riego");
            imagen.setImageResource(R.drawable.ic_irrigation);
        }

        if(actuatorData.getIsOn() == 1){
            // 1 - ESTÁ ENCENDIDO
            switchAbierto.setChecked(Boolean.TRUE);
        } else {
            switchAbierto.setChecked(Boolean.FALSE);
        }

        if(actuatorData.getAffects().contains("Temperature")){
            temp.setChecked(Boolean.TRUE);
        } else {
            temp.setChecked(Boolean.FALSE);
        }
        if(actuatorData.getAffects().contains("Luminosity")){
            lum.setChecked(Boolean.TRUE);
        } else {
            lum.setChecked(Boolean.FALSE);
        }
        if(actuatorData.getAffects().contains("Air Quality")){
            calidad.setChecked(Boolean.TRUE);
        } else {
            calidad.setChecked(Boolean.FALSE);
        }
        if(actuatorData.getAffects().contains("Humidity")){
            hum.setChecked(Boolean.TRUE);
        } else {
            hum.setChecked(Boolean.FALSE);
        }

        Button cancelar = findViewById(R.id.buttonSID);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cambios cancelados", Toast.LENGTH_SHORT);
                Intent intent = new Intent(StateIrrigationSprinklersDetail.this, StateFragment.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_GREENHOUSE, actuatorData.getIdGreenhouse());
                intent.putExtras(bundle);
                finish();
            }
        });
        Button guardar = findViewById(R.id.button2SID);
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
                int ison = 0;
                if(switchAbierto.isChecked()){
                    ison = 1;
                }

                ActuatorMessage actuatorToSave = new ActuatorMessage(actuatorData.getId(), actuatorData.getIdGreenhouse(), ison, affects);
                Call<MessageResponse> login;

                if(actuatorData.getClassType().equals("Sprinklers")){
                    login = mInformNet.getApi().putSprinklers(actuatorToSave);
                } else {
                    login = mInformNet.getApi().putIrrigation(actuatorToSave);
                }


                login.enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        MessageResponse mgResponse = response.body();
                        if(mgResponse.getAffectedRows() == 1) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable(GreenhouseActivity.EXTRA_GREENHOUSE, mGreenhouse);
                            Intent intent = new Intent(StateIrrigationSprinklersDetail.this, GreenhouseActivity.class);
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
