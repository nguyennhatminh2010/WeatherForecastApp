package com.example.weatherforecast.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.model.itemRow;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<itemRow> items;
    Context context;

    public ItemAdapter(ArrayList<itemRow> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_row_homedown,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtHour.setText(items.get(position).getHour());
        holder.txtDay.setText(items.get(position).getDay());
        holder.txtTemperature.setText(items.get(position).getTemperature());
        holder.txtRealFeel.setText(items.get(position).getRealFeel());
        holder.txtHumidity.setText(items.get(position).getHumidity());
        holder.txtDroplets.setText(items.get(position).getDroplets());
        holder.imgDrop.setImageResource(items.get(position).getDrop());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtHour, txtDay, txtHumidity, txtTemperature, txtRealFeel, txtDroplets;
        ImageView imgDrop;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHour = itemView.findViewById(R.id.textView_Hour);
            txtDay = itemView.findViewById(R.id.textView_Day);
            txtHumidity = itemView.findViewById(R.id.textView_Humidity);
            txtTemperature = itemView.findViewById(R.id.textView_Temperature);
            txtRealFeel = itemView.findViewById(R.id.textView_Realfeel);
            txtDroplets = itemView.findViewById(R.id.textView_Droplets);
            imgDrop     = itemView.findViewById(R.id.imageView_Drop);
        }
    }
}
