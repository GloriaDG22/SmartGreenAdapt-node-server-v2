package es.unex.smartgreenadapt.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.R;
import es.unex.smartgreenadapt.model.MessageNotification;
import es.unex.smartgreenadapt.model.Notification;

public class ListNotificationAdapter extends RecyclerView.Adapter<ListNotificationAdapter.ViewHolder> {

    private static ListNotificationAdapter mInstance;

    private Context context;
    private OnNotListener mOnNotListener;

    // Lista de notificaciones que muestra el adapter
    private List<MessageNotification> mNotificationList ;

    private LayoutInflater mInflater;

    private boolean mSugerencia = true;

    public static synchronized ListNotificationAdapter getInstance(@NonNull LayoutInflater inflater, Context context, OnNotListener mOnNotListener){
        if(mInstance == null){
            mInstance = new ListNotificationAdapter(inflater, context, mOnNotListener);
        }
        return mInstance;
    }

    public ListNotificationAdapter(@NonNull LayoutInflater inflater, Context context, OnNotListener onNotListener) {
        this.context = context;
        mInflater = inflater;
        mOnNotListener = onNotListener;
        mNotificationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.item_notificaction, parent, false);
        return new ViewHolder(root, mOnNotListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageNotification notification = mNotificationList.get(position);

        try {
            holder.date.setText(notification.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.textNotification.setText(notification.getDescription());

        int image = 0;
        int color = 0;
        if(notification.isWarning()) {
            image = R.drawable.ic_baseline_warning_24;
            color = R.color.warning_200;
        }
        else {
            image = R.drawable.ic_baseline_error_24;
            color = R.color.error_200;
        }

        holder.ic_item.setImageResource(image);
        holder.linearLayout.setBackgroundResource(color);
    }

    @Override
    public int getItemCount() {
        return this.mNotificationList.size();
    }

    public void setmOnNotificationListener(OnNotListener onNotListener) {
        this.mOnNotListener = onNotListener;
    }

    public void allListNotifications(ArrayList<MessageNotification> listNotifications) {
        mNotificationList.clear();
        mNotificationList.addAll(listNotifications);
        notifyDataSetChanged();
    }

    public interface OnNotListener{
        void onNotificationClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView date, textNotification;
        private ImageView ic_item;
        private LinearLayout linearLayout;
        OnNotListener onNotListener;

        public ViewHolder(View view, OnNotListener onNotListener) {
            super(view);
            date = view.findViewById(R.id.date);
            textNotification = view.findViewById(R.id.text_notification);
            ic_item = view.findViewById(R.id.ic_item);

            linearLayout = view.findViewById(R.id.linearLayout);

            this.onNotListener = onNotListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNotListener.onNotificationClick(getAdapterPosition());
        }
    }
}
