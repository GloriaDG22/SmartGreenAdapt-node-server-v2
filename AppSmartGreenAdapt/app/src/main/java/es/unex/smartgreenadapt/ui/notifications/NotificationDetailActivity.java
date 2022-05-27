package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.data.remote.InformationNetworkLoaderRunnable;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;
import es.unex.smartgreenadapt.model.greenhouse.WeatherResponse;
import es.unex.smartgreenadapt.model.greenhouse.information.AirQuality;
import es.unex.smartgreenadapt.model.greenhouse.information.Humidity;
import es.unex.smartgreenadapt.model.greenhouse.information.Luminosity;
import es.unex.smartgreenadapt.model.greenhouse.information.Temperature;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetailActivity extends Fragment {


    private TextView name, recommendations, valid_value, type, text_recommendations;
    private MessageNotification mNotification;
    private ListInformation mListInfo;

    private InformationNetworkLoaderRunnable mInformNet;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Entra en el detalle notificacion");
        //super.onCreate(saveInstanceState);

        mListInfo = ListInformation.getInstance();
        View root = inflater.inflate(R.layout.activity_notification_detail, container, false);
        //setContentView(R.layout.activity_notification_detail);

        //((AppCompatActivity)getActivity()).setSupportActionBar(root.findViewById(R.id.toolbar_general));
        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Detail notification");

        Bundle bundle =  getArguments();
        mNotification = (MessageNotification) bundle.getSerializable(NotificationsFragment.EXTRA_NOTIFICATION);

        name = root.findViewById(R.id.text_notification);
        type = root.findViewById(R.id.value_type);
        valid_value = root.findViewById(R.id.value_valid_values);
        recommendations = root.findViewById(R.id.value_recommendations);
        text_recommendations = root.findViewById(R.id.text_recommendations);


        if (mNotification.isWarning()) {
            int pos = mListInfo.getPositionTitle(mNotification.getProblem());
            valid_value.setText("The " + mNotification.getProblem().toLowerCase(Locale.ROOT) + " value should be between " + mListInfo.getValueMin(pos) + " and " + mListInfo.getValueMax(pos) + ".");
        } else
            valid_value.setText("The value is wrong. You need to contact the support team.");

        String stringType;
        if (mNotification.isWarning()) {
            stringType = "Warning";
            text_recommendations.setVisibility(View.VISIBLE);
        }else {
            stringType = "Error";
            text_recommendations.setVisibility(View.INVISIBLE);
        }
        type.setText(stringType);

        setInformation();
        return root;
    }

    private void setInformation() {
        mInformNet = InformationNetworkLoaderRunnable.getInstance();
        switch (mNotification.getProblem()) {
            case "Temperature":
                Call<Temperature> responseTem = mInformNet.getApi().getCurrentTemperature(mNotification.getIdGreenhouse());
                responseTem.enqueue(new Callback<Temperature>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<Temperature> call, @NonNull Response<Temperature> response) {
                        if (response.isSuccessful()) {
                            Temperature temp = response.body();
                            temp.setLists();
                            name.setText("The " + mNotification.getProblem() + " is " + temp.getAmount() + "º");

                            if (mNotification.isWarning()) setRecommendations(temp.getListStrings(), temp.getListInts());
                        }
                    }

                    @Override
                    public void onFailure(Call<Temperature> call, Throwable t) {
                        //vacio
                    }
                });
                break;
            case "Luminosity":
                Call<Luminosity> responseLum = mInformNet.getApi().getCurrentLuminosity(mNotification.getIdGreenhouse());
                responseLum.enqueue(new Callback<Luminosity>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<Luminosity> call, @NonNull Response<Luminosity> response) {
                        if (response.isSuccessful()) {
                            Luminosity luminosity = response.body();
                            luminosity.setLists();
                            name.setText("The " + mNotification.getProblem() + " is " + luminosity.getAmount() + "");

                            if (mNotification.isWarning()) setRecommendations(luminosity.getListStrings(), luminosity.getListInts());
                        }
                    }

                    @Override
                    public void onFailure(Call<Luminosity> call, Throwable t) {
                        //vacio
                    }
                });
                break;
            case "AirQuality":
                Call<AirQuality> responseAir = mInformNet.getApi().getCurrentAirQuality(mNotification.getIdGreenhouse());
                responseAir.enqueue(new Callback<AirQuality>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<AirQuality> call, @NonNull Response<AirQuality> response) {
                        if (response.isSuccessful()) {
                            AirQuality airQuality = response.body();
                            airQuality.setLists();
                            name.setText("The " + mNotification.getProblem() + " is " + airQuality.getAmount() + "");

                            if (mNotification.isWarning()) setRecommendations(airQuality.getListStrings(), airQuality.getListInts());
                        }
                    }

                    @Override
                    public void onFailure(Call<AirQuality> call, Throwable t) {
                        //vacio
                    }
                });
                break;
            case "Humidity":
                Call<Humidity> responseHum = mInformNet.getApi().getCurrentHumidity(mNotification.getIdGreenhouse());
                responseHum.enqueue(new Callback<Humidity>() {
                    @Override
                    public void onResponse(@NonNull Call<Humidity> call, @NonNull Response<Humidity> response) {
                        if (response.isSuccessful()) {
                            Humidity humidity = response.body();
                            humidity.setLists();
                            name.setText("The " + mNotification.getProblem() + " is " + humidity.getAmount() + "%");

                            if (mNotification.isWarning()) setRecommendations(humidity.getListStrings(), humidity.getListInts());
                        }
                    }

                    @Override
                    public void onFailure(Call<Humidity> call, Throwable t) {
                        //vacio
                    }
                });
                break;
        }
    }

    public void setRecommendations(ArrayList<String> listStrings, ArrayList<Integer> listInts) {
        int i = 0;
        StringBuilder textRec = new StringBuilder();
        for (String infor : listStrings) {
            textRec.append("- You can ");
            if (mNotification.getStatus().equals("high")) {
                if (listInts.get(i) == 0) textRec.append("open ");
                else textRec.append("close ");
            } else {
                if (listInts.get(i) == 0) textRec.append("close ");
                else textRec.append("open ");
            }
            textRec.append("the " + infor + ".\n");
            i++;
        }

        recommendations.setText(textRec);

    }

   /* @Override
    public void onNotificationClick(int position) {

    }

    // Seleción del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
