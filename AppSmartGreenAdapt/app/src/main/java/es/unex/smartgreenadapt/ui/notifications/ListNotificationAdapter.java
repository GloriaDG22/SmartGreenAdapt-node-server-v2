package es.unex.smartgreenadapt.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.Notification;

public class ListNotificationAdapter extends RecyclerView.Adapter<ListNotificationAdapter.ViewHolder> {

    private static ListNotificationAdapter mInstance;

    private Context context;
    private OnNotListener mOnNotListener;

    // Lista de notificaciones que muestra el adapter
    private List<Notification> mNotificationList ;

    private LayoutInflater mInflater;

    private boolean mSugerencia = true;

    public static synchronized ListNotificationAdapter getInstance(@NonNull LayoutInflater inflater, Context context){
        if(mInstance == null){
            mInstance = new ListNotificationAdapter(inflater, context);
        }
        return mInstance;
    }

    public ListNotificationAdapter(@NonNull LayoutInflater inflater, Context context) {
        this.context = context;
        mInflater = inflater;
        mNotificationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.item_notificaction, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = mNotificationList.get(position);

        holder.date.setText(notification.getDate());
        holder.textNotification.setText(notification.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.mNotificationList.size();
    }

    public void allListNotifications(List<Notification> listNotifications) {
        mNotificationList.clear();
        mNotificationList.addAll(listNotifications);
        notifyDataSetChanged();
    }

    public interface OnNotListener{
        void onNotClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView date, textNotification;
        OnNotListener onNotListener;

        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            textNotification = view.findViewById(R.id.text_notification);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNotListener.onNotClick(getAdapterPosition());
        }
    }
}
