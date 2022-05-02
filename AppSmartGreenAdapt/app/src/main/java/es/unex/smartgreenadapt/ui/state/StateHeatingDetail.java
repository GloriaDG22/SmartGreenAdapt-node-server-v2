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

import org.w3c.dom.Text;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.model.greenhouse.actuators.HeatingMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateHeatingDetail extends AppCompatActivity {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";
    private ActuatorAllData heatingData;
    private MessageGreenhouse mGreenhouse;
    private TextView titulo;
    private ImageView imagen;
    private CheckBox temp, calidad, lum, hum;
    private EditText potencia, tipo;

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        System.out.println(" Entra en el detalle de ventana ");
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Bundle bundle = getIntent().getExtras();

        setContentView(R.layout.activity_heating_detail);
        heatingData = (ActuatorAllData) bundle.getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable("GREENHOUSE");
        titulo = (TextView) findViewById(R.id.textViewTituloHD);
        imagen = findViewById(R.id.imageViewHD);

        temp = findViewById(R.id.checkBoxHD);
        calidad = findViewById(R.id.checkBox2HD);
        lum = findViewById(R.id.checkBox3HD);
        hum = findViewById(R.id.checkBox4HD);
        tipo = (EditText) findViewById(R.id.tipoEdittext);
        potencia = (EditText) findViewById(R.id.potenciaHeating);

        //email@gmail.com

        // seteo de datos
        int value = heatingData.getValue();
        String tipoS = heatingData.getHeatingType().toString();
        titulo.setText("Calefactor");
        potencia.setText(Integer.toString(value));
        tipo.setText(tipoS);
        imagen.setImageResource(R.drawable.ic_heating_svgrepo_com);

        if(heatingData.getAffects().contains("Temperature")){
            temp.setChecked(Boolean.TRUE);
        } else {
            temp.setChecked(Boolean.FALSE);
        }
        if(heatingData.getAffects().contains("Luminosity")){
            lum.setChecked(Boolean.TRUE);
        } else {
            lum.setChecked(Boolean.FALSE);
        }
        if(heatingData.getAffects().contains("Air Quality")){
            calidad.setChecked(Boolean.TRUE);
        } else {
            calidad.setChecked(Boolean.FALSE);
        }
        if(heatingData.getAffects().contains("Humidity")){
            hum.setChecked(Boolean.TRUE);
        } else {
            hum.setChecked(Boolean.FALSE);
        }

        Button cancelar = findViewById(R.id.buttonHD);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cambios cancelados", Toast.LENGTH_SHORT);
                Intent intent = new Intent(StateHeatingDetail.this, StateFragment.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(EXTRA_GREENHOUSE, heatingData.getIdGreenhouse());
                intent.putExtras(bundle);
                finish();
            }
        });
        Button guardar = findViewById(R.id.button2HD);
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

                String tipoH = tipo.getText().toString();
                int potenciaH = Integer.parseInt(potencia.getText().toString());

                HeatingMessage heatingToSave = new HeatingMessage(heatingData.getId(), tipoH, heatingData.getIdGreenhouse(), potenciaH, affects);


                Call<MessageResponse> login = mInformNet.getApi().putHeating(heatingToSave);

                if(potenciaH <= 100 || potenciaH >= 0 ){
                    login.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse mgResponse = response.body();
                            if(mgResponse.getAffectedRows() == 1) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(GreenhouseActivity.EXTRA_GREENHOUSE, mGreenhouse);
                                Intent intent = new Intent(StateHeatingDetail.this, GreenhouseActivity.class);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "La potencia debe tener un valor entre 0 y 100", Toast.LENGTH_SHORT);
                }

            }
        });
    }
}
