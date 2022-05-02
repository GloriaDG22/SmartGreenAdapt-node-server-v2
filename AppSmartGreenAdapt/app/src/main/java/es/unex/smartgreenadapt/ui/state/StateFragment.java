package es.unex.smartgreenadapt.ui.state;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.GreenhouseActivity;
import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Heating;
import es.unex.smartgreenadapt.model.greenhouse.actuators.HeatingMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Irrigation;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Sprinklers;
import es.unex.smartgreenadapt.model.greenhouse.actuators.WindowMessage;
import es.unex.smartgreenadapt.model.greenhouse.actuators.Windows;
import es.unex.smartgreenadapt.ui.notifications.NotificationDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateFragment extends Fragment implements StateAdapter.OnStateListener{

    private StateViewModel stateViewModel;
    public static final String EXTRA_STATE = "STATE";
    private StateAdapter listSAdapter;
    private RecyclerView mRecyclerView;

    private TextView textNoHayEstados;

    // Listado de actuadores
    Windows windowList = new Windows();
    Sprinklers sprinklersList = new Sprinklers();
    Heating heatingList = new Heating();
    Irrigation irrigationList = new Irrigation();

    MessageGreenhouse mGreenhouse;

    private TextView textNoEstados;

    ActuatorAllData mActuatorData;

    private InformationNetworkLoaderRunnable mInformNet;

    int idUsuario;

    ArrayList<ActuatorAllData> allData = new ArrayList<>();
    int times;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_state, container, false);

        mRecyclerView = root.findViewById(R.id.stateRV);

        listSAdapter = StateAdapter.getInstance(inflater, this.getContext(), this);

        textNoEstados = root.findViewById(R.id.text_noStates);

        Bundle bundle = getActivity().getIntent().getExtras();
        mGreenhouse = (MessageGreenhouse) bundle.getSerializable(GreenhouseActivity.EXTRA_GREENHOUSE);
        idUsuario = mGreenhouse.getIdUsername();
        getStates();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        listSAdapter.resetAllData(allData);
        if(allData.isEmpty()){
            textNoEstados.setVisibility(View.VISIBLE);
        } else {
            textNoEstados.setVisibility(View.INVISIBLE);
        }

        mRecyclerView.setAdapter(listSAdapter);
        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(null);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }


    private void getStates(){
        times = 0;
        mInformNet = InformationNetworkLoaderRunnable.getInstance();

        Call<Windows> responseWin = mInformNet.getApi().getWindows(mGreenhouse.getId());
        responseWin.enqueue(new Callback<Windows>() {
            @Override
            public void onResponse(@NonNull Call<Windows> call, @NonNull Response<Windows> response) {
                if (response.isSuccessful()) {
                    Windows allWindows = response.body();

                    for(int i = 0; i < allWindows.getAll().size() ; i++){
                        WindowMessage window = allWindows.getAll().get(i);
                        ActuatorAllData actuatorData = new ActuatorAllData();
                        actuatorData.setClassType("Window");
                        actuatorData.setIdGreenhouse(mGreenhouse.getId());
                        actuatorData.setName(window.getName());
                        actuatorData.setIsOn(window.getIsOn());
                        actuatorData.setAffects(window.getAffects());
                        actuatorData.setId(window.getId());
                        allData.add(actuatorData);
                    }
                    times++;
                    if(times == 4){
                        onResume();
                    }
                }
            }

            @Override
            public void onFailure(Call<Windows> call, Throwable t) {
                //vacio
            }
        });

        Call<Heating> responseHea = mInformNet.getApi().getHeating(mGreenhouse.getId());
        responseHea.enqueue(new Callback<Heating>() {
            @Override
            public void onResponse(@NonNull Call<Heating> call, @NonNull Response<Heating> response) {
                if (response.isSuccessful()) {
                    Heating allHeating = response.body();

                    for(int i = 0; i < allHeating.getAll().size() ; i++){
                        HeatingMessage heat = allHeating.getAll().get(i);
                        ActuatorAllData actuatorData = new ActuatorAllData();
                        actuatorData.setClassType("Heating");
                        actuatorData.setIdGreenhouse(mGreenhouse.getId());
                        actuatorData.setValue(heat.getValue());
                        actuatorData.setHeatingType(heat.getType());
                        actuatorData.setAffects(heat.getAffects());
                        actuatorData.setId(heat.getId());
                        allData.add(actuatorData);
                    }
                    times++;
                    if(times == 4){
                        onResume();
                    }
                }
            }

            @Override
            public void onFailure(Call<Heating> call, Throwable t) {
                //vacio
            }
        });

        Call<Sprinklers> responseSp = mInformNet.getApi().getSprinklers(mGreenhouse.getId());
        responseSp.enqueue(new Callback<Sprinklers>() {
            @Override
            public void onResponse(@NonNull Call<Sprinklers> call, @NonNull Response<Sprinklers> response) {
                if (response.isSuccessful()) {
                    Sprinklers allSprinklers = response.body();

                    for(int i = 0; i < allSprinklers.getAll().size() ; i++){
                        ActuatorMessage sprinklers = allSprinklers.getAll().get(i);
                        ActuatorAllData actuatorData = new ActuatorAllData();
                        actuatorData.setClassType("Sprinklers");
                        actuatorData.setIdGreenhouse(mGreenhouse.getId());
                        actuatorData.setId(sprinklers.getId());
                        actuatorData.setAffects(sprinklers.getAffects());
                        actuatorData.setIsOn(sprinklers.getIsOn());
                        allData.add(actuatorData);
                    }
                    times++;
                    if(times == 4){
                        onResume();
                    }
                }
            }

            @Override
            public void onFailure(Call<Sprinklers> call, Throwable t) {
                //vacio
            }
        });

        Call<Irrigation> responseIrr = mInformNet.getApi().getIrrigation(mGreenhouse.getId());
        responseIrr.enqueue(new Callback<Irrigation>() {
            @Override
            public void onResponse(@NonNull Call<Irrigation> call, @NonNull Response<Irrigation> response) {
                if (response.isSuccessful()) {
                    Irrigation allIrrigation = response.body();

                    for(int i = 0; i < allIrrigation.getAll().size() ; i++){
                        ActuatorMessage sprinklers = allIrrigation.getAll().get(i);
                        ActuatorAllData actuatorData = new ActuatorAllData();
                        actuatorData.setClassType("Irrigation");
                        actuatorData.setIdGreenhouse(mGreenhouse.getId());
                        actuatorData.setId(sprinklers.getId());
                        actuatorData.setAffects(sprinklers.getAffects());
                        actuatorData.setIsOn(sprinklers.getIsOn());
                        allData.add(actuatorData);
                    }
                    times++;
                    if(times == 4){
                        onResume();
                    }
                }
            }

            @Override
            public void onFailure(Call<Irrigation> call, Throwable t) {
                //vacio
            }
        });


    }

    @Override
    public void onStateClick(int position) {
        mActuatorData = allData.get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_STATE, mActuatorData);
        bundle.putSerializable("GREENHOUSE", mGreenhouse);
        Intent intent;

        if("Window".equals(mActuatorData.getClassType())){
            System.out.println("Entró en el if de ventana");
            intent = new Intent(getContext(), StateWindowDetail.class);
            intent.putExtras(bundle);
        } else if("Heating".equals(mActuatorData.getClassType())){
            intent = new Intent(getContext(), StateHeatingDetail.class);
            intent.putExtras(bundle);
        } else if("Sprinklers".equals(mActuatorData.getClassType())){
            intent = new Intent(getContext(), StateIrrigationSprinklersDetail.class);
            intent.putExtras(bundle);
        } else {
            intent = new Intent(getContext(), StateIrrigationSprinklersDetail.class);
            intent.putExtras(bundle);
        }

        getContext().startActivity(intent);
        System.out.println("Después del startActivity");
    }
}