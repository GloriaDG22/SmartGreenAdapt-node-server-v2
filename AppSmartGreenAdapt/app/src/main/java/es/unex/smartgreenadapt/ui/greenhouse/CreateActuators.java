package es.unex.smartgreenadapt.ui.greenhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.MessageResponse;
import es.unex.smartgreenadapt.model.greenhouse.Greenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.HeatingMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateActuators extends AppCompatActivity {

    private EditText tipo, nombreVentana;
    int contadorRiego, contadorAspersor;
    Greenhouse mListButtons = new Greenhouse();

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_actuators);
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        nombreVentana = (EditText) findViewById(R.id.windowNameEditText);
        tipo = (EditText) findViewById(R.id.tipoEdittextCreacion);
        // Obtención del id del invernadero creado

        Bundle args = getIntent().getExtras();
        Integer idGreenhouse = (Integer) args.getSerializable("IDGREENHOUSE");
        MessageGreenhouse greenhouse = (MessageGreenhouse) args.getSerializable("GREENHOUSE");
        greenhouse.setId(idGreenhouse);
        // creacion de los botonesd de la pantalla

        Button guardarVentana = findViewById(R.id.botonGuardarVentana);
        Button guardarCalefactor = findViewById(R.id.botonGuardarCalefactor);
        Button guardarAspersor = findViewById(R.id.botonGuardarAspersor);
        Button guardarRiego = findViewById(R.id.botonCrearRiego);
        Button guardarInvernadero = findViewById(R.id.botonGuardarInvernadero);


        // Contadores: solo puede haber un elemento de cada por invernadero
        contadorAspersor = 0;
        contadorRiego = 0;

        // Crear ventana
        guardarVentana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nombreVentana.getText().toString() != null && !nombreVentana.getText().toString().isEmpty()){
                    WindowMessage wToSave = new WindowMessage(0, nombreVentana.getText().toString(), idGreenhouse, 0, "Temperature,Airquality,Luminosity,Humidity");
                    Call<MessageResponse> login = mInformNet.getApi().postWindow(wToSave);

                    login.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse mgResponse = response.body();
                            if(mgResponse.getAffectedRows() == 1) {
                                //GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                                //activity.volverAlListado(mGreenhouse);
                                Toast.makeText(getApplicationContext(), "Ventana creada", Toast.LENGTH_SHORT);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                }

            }
        });

        // Crear calefactores
        guardarCalefactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tipo.getText().toString() != null && !tipo.getText().toString().isEmpty()){
                    HeatingMessage hToSave = new HeatingMessage(0, tipo.getText().toString(), idGreenhouse, 0, "Temperature,Humidity");
                    Call<MessageResponse> login = mInformNet.getApi().postHeating(hToSave);

                    login.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse mgResponse = response.body();
                            if(mgResponse.getAffectedRows() == 1) {
                                //GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                                //activity.volverAlListado(mGreenhouse);
                                Toast.makeText(getApplicationContext(), "Calefactor creado", Toast.LENGTH_SHORT);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                }

            }
        });

        //Crear aspersor
        guardarAspersor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contadorAspersor == 0){
                    ActuatorMessage wToSave = new ActuatorMessage(0,  idGreenhouse, 0, "Temperature,Humidity");
                    Call<MessageResponse> login = mInformNet.getApi().postSprinklers(wToSave);

                    login.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse mgResponse = response.body();
                            if(mgResponse.getAffectedRows() == 1) {
                                contadorAspersor++;
                                //GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                                //activity.volverAlListado(mGreenhouse);
                                Toast.makeText(getApplicationContext(), "Aspersores creados", Toast.LENGTH_SHORT);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Solo puede haber un aspersor por invernadero.", Toast.LENGTH_SHORT);

                }

            }
        });


        guardarRiego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contadorRiego == 0){
                    ActuatorMessage wToSave = new ActuatorMessage(0,  idGreenhouse, 0, "Humidity");
                    Call<MessageResponse> login = mInformNet.getApi().postIrrigation(wToSave);

                    login.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            MessageResponse mgResponse = response.body();
                            if(mgResponse.getAffectedRows() == 1) {
                                contadorRiego++;
                                //GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                                //activity.volverAlListado(mGreenhouse);
                                Toast.makeText(getApplicationContext(), "Riego creado", Toast.LENGTH_SHORT);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Solo puede haber un riego por invernadero", Toast.LENGTH_SHORT);

                }

            }
        });

        // Boton guardar Invernadero
        guardarInvernadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(greenhouse != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(GreenhouseActivity.EXTRA_GREENHOUSE, greenhouse);

                    Log.println(Log.ASSERT, "Result Greenhouse adapter", greenhouse.getName());

                    Intent intent = new Intent(getApplicationContext(), GreenhouseActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }

    }
