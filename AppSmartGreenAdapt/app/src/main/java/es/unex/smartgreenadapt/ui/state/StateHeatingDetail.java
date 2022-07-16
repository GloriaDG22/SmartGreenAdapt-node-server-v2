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

public class StateHeatingDetail extends Fragment {

    public static final String EXTRA_GREENHOUSE = "ID_GREENHOUSE";
    private ActuatorAllData heatingData;
    private MessageGreenhouse mGreenhouse;
    private TextView titulo;
    private ImageView imagen;
    private EditText potencia, tipo;

    private InformationNetworkLoaderRunnable mInformNet;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.activity_heating_detail, container, false);;
        System.out.println(" Entra en el detalle de ventana ");
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Bundle args = getArguments();
        heatingData = (ActuatorAllData) args.getSerializable(StateFragment.EXTRA_STATE);
        mGreenhouse = (MessageGreenhouse) args.getSerializable("GREENHOUSE");
        titulo = (TextView) root.findViewById(R.id.textViewTituloHD);
        imagen = root.findViewById(R.id.imageViewHD);

        tipo = (EditText) root.findViewById(R.id.tipoEdittext);
        potencia = (EditText) root.findViewById(R.id.potenciaHeating);

        //email@gmail.com

        // seteo de datos
        int value = heatingData.getValue();
        String tipoS = heatingData.getHeatingType().toString();
        titulo.setText("Calefactor");
        potencia.setText(Integer.toString(value));
        tipo.setText(tipoS);
        imagen.setImageResource(R.drawable.ic_heating_svgrepo_com);

        Button cancelar = root.findViewById(R.id.buttonHD);
        // Funcionalidad boton de cancelar
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cambios cancelados", Toast.LENGTH_SHORT);
                GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                activity.volverAlListado(mGreenhouse);
            }
        });
        Button guardar = root.findViewById(R.id.button2HD);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String affects = "Temperature,Humidity";

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
                                GreenhouseActivity activity = (GreenhouseActivity) requireActivity();
                                activity.volverAlListado(mGreenhouse);
                            }

                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "La potencia debe tener un valor entre 0 y 100", Toast.LENGTH_SHORT);
                }

            }
        });

        return root;
    }
}
