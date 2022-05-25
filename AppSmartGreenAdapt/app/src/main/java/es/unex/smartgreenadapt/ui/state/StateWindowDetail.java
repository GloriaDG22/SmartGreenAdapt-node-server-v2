package es.unex.smartgreenadapt.ui.state;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class StateWindowDetail extends Fragment {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";
    private ActuatorAllData windowData;
    private MessageGreenhouse mGreenhouse;
    private TextView titulo;
    private ImageView imagen;
    private Switch switchAbierto;
    private EditText nombre;

    private InformationNetworkLoaderRunnable mInformNet;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_window_detail, container, false);
        mInformNet = InformationNetworkLoaderRunnable.getInstance();
        Bundle args = getArguments();
        windowData = (ActuatorAllData) args
                .getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) args
                .getSerializable("GREENHOUSE");

        titulo = root.findViewById(R.id.textViewTituloWD);
        imagen = root.findViewById(R.id.imageViewWD);
        switchAbierto = root.findViewById(R.id.isOnSwitchWD);

        nombre = root.findViewById(R.id.windowNameEditText);

        // seteo de datos
        titulo.setText("Ventana");
        imagen.setImageResource(R.drawable.ic_window_svgrepo_com);
        if(windowData.getIsOn() == 1){
            // 1 - LA VENTANA ESTÁ ABIERTA
            switchAbierto.setChecked(Boolean.TRUE);
        } else {
            switchAbierto.setChecked(Boolean.FALSE);
        }



        nombre.setText(windowData.getName());

        Button cancelar = root.findViewById(R.id.buttonWD);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cambios cancelados", Toast.LENGTH_SHORT);
                GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                activity.volverAlListado(mGreenhouse);
            }
        });
        Button guardar = root.findViewById(R.id.button2WD);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String affects  = "Luminosidad, Calidad de Aire, Temperatura";


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
                            System.out.println("Se ha guardado");

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
