package es.unex.smartgreenadapt.ui.state;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class StateIrrigationSprinklersDetail extends Fragment {

    private ActuatorAllData actuatorData;
    private TextView titulo;
    private ImageView imagen;
    private Switch switchAbierto;
    private MessageGreenhouse mGreenhouse;

    private InformationNetworkLoaderRunnable mInformNet;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        System.out.println(" Entra en el detalle de riego y aspersores ");
        mInformNet = InformationNetworkLoaderRunnable.getInstance();
        View root = inflater.inflate(R.layout.activity_sprinklers_irrigation_detail, container, false);
        Bundle bundle = getArguments();

        actuatorData = (ActuatorAllData) bundle.getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable("GREENHOUSE");

        titulo = root.findViewById(R.id.textViewTituloSID);
        imagen = root.findViewById(R.id.imageViewSID);
        switchAbierto = root.findViewById(R.id.isOnSwitchSID);

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

        Button cancelar = root.findViewById(R.id.buttonSID);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                activity.volverAlListado(mGreenhouse);
            }
        });
        Button guardar = root.findViewById(R.id.button2SID);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String affects  = "Humedad";

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
                            GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                            activity.volverAlListado(mGreenhouse);
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {

                    }
                });

            }
        });

        return root;
    }

}
