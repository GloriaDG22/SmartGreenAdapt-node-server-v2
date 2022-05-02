package es.unex.smartgreenadapt.ui.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorAllData;
import es.unex.smartgreenadapt.model.greenhouse.actuators.ActuatorMessage;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder>{

    private static StateAdapter mInstance;

    private Context context;
    private OnStateListener mOnStateListener;

    private List<ActuatorAllData> mAllData;

    private LayoutInflater mInflater;

    public StateAdapter(LayoutInflater inflater, Context context, OnStateListener onStateListener) {
        this.context = context;
        this.mInflater = inflater;
        this.mOnStateListener = onStateListener;
        this.mAllData = new ArrayList<>();
    }

    public static synchronized StateAdapter getInstance(@NonNull LayoutInflater inflater, Context context, OnStateListener onStateListener){
        if(mInstance == null){
            mInstance = new StateAdapter(inflater, context, onStateListener);
        }
        return mInstance;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View root = mInflater.inflate(R.layout.item_state, parent, false);
        return new ViewHolder(root, mOnStateListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder view, int i) {
        ActuatorAllData data = mAllData.get(i);

        view.type.setText(data.getClassType());
        if("Window".equals(data.getClassType())){
            view.moreInfo.setText(data.getName());
            view.img.setImageResource(R.drawable.ic_window_svgrepo_com);
        } else if("Heating".equals(data.getClassType())){
            view.moreInfo.setText(data.getHeatingType());
            view.img.setImageResource(R.drawable.ic_heating_svgrepo_com);
        } else if("Sprinklers".equals(data.getClassType())){
            view.moreInfo.setText("");
            view.img.setImageResource(R.drawable.ic_irrigation_svgrepo_com);
        } else {
            view.moreInfo.setText("");
            view.img.setImageResource(R.drawable.ic_irrigation);
        }
    }

    @Override
    public int getItemCount() {
        return this.mAllData.size();
    }

    public void resetAllData(ArrayList<ActuatorAllData> listData){
        this.mAllData.clear();
        this.mAllData.addAll(listData);
        notifyDataSetChanged();
    }

    public interface OnStateListener{
        void onStateClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView type, moreInfo;
        private ImageView img;
        private LinearLayout linearLayout;
        OnStateListener onStateListener;

        public ViewHolder(View view, OnStateListener onStateListener){
            super(view);
            type = view.findViewById(R.id.type);
            moreInfo = view.findViewById(R.id.moreInfo);
            img = view.findViewById(R.id.ic_itemState);

            linearLayout = view.findViewById(R.id.linearLayoutState);

            this.onStateListener = onStateListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStateListener.onStateClick(getAdapterPosition());
        }
    }
}
