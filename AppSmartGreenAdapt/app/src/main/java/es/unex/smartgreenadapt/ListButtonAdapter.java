package es.unex.smartgreenadapt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import es.unex.smartgreenadapt.model.greenhouse.Greenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageGreenhouse;
import es.unex.smartgreenadapt.model.greenhouse.MessageNotification;


public class ListButtonAdapter extends RecyclerView.Adapter<ListButtonAdapter.ViewHolder> {

    private static ListButtonAdapter mInstance;

    private List<MessageGreenhouse> mButtons;
    private LayoutInflater mInflater;

    private Context mContext;

    public static synchronized ListButtonAdapter getInstance(@NonNull LayoutInflater inflater, Context context) {
        if (mInstance == null) {
            mInstance = new ListButtonAdapter(inflater, context);
        }
        return mInstance;
    }

    public ListButtonAdapter(@NonNull LayoutInflater inflater, Context context) {
        mContext = context;
        mInflater = inflater;
        mButtons = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.item_button, parent, false);
        return new ListButtonAdapter.ViewHolder(root);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ListButtonAdapter.ViewHolder holder, int position) {
        MessageGreenhouse greenhouse = mButtons.get(position);
        holder.mButton.setText(greenhouse.getName());

        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ListGreenhousesActivity.EXTRA_GREENHOUSE, greenhouse);

                Intent intent = new Intent(mContext, GreenhouseActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mButtons.size();
    }

    public void allListButtons(ArrayList<MessageGreenhouse> listButtons) {
        mButtons.clear();
        mButtons.addAll(listButtons);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button mButton;

        public ViewHolder(View view) {
            super(view);
            mButton = view.findViewById(R.id.button_greenhouse);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
