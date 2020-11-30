package com.example.weatherforecast.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.R;
import com.example.weatherforecast.model.ListItem;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<ListItem> items;
    Context context;

    public ItemAdapter(ArrayList<ListItem> items, Context context) {
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
        ListItem item = items.get(position);
        String day = item.getDtTxt().substring(0,item.getDtTxt().indexOf(" ")).substring(8,10).concat("/");
        String month = item.getDtTxt().substring(0,item.getDtTxt().indexOf(" ")).substring(5,7);
        String hour  = item.getDtTxt().substring(item.getDtTxt().indexOf(" ")).substring(0,3).concat("h");
        holder.txtHour.setText(hour);
        holder.txtDay.setText(day.concat(month));
        double temperature = item.getMain().getTemp() - 273.15;
        holder.txtTemperature.setText((int)temperature + "");
        holder.txtRealFeel.setText((int)(item.getMain().getFeelsLike() - 273.15)+ "");
        holder.txtHumidity.setText(item.getMain().getHumidity() + "");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtHour, txtDay, txtHumidity, txtTemperature, txtRealFeel, txtDroplets;
        ImageView imgDrop;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHour         = itemView.findViewById(R.id.textView_Hour);
            txtDay          = itemView.findViewById(R.id.textView_Day);
            txtHumidity     = itemView.findViewById(R.id.textView_Humidity);
            txtTemperature  = itemView.findViewById(R.id.textView_Temperature);
            txtRealFeel     = itemView.findViewById(R.id.textView_Realfeel);
            txtDroplets     = itemView.findViewById(R.id.textView_Droplets);
            imgDrop         = itemView.findViewById(R.id.imageView_Drop);


            layout = itemView.findViewById(R.id.item_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", getAdapterPosition());
//                    bundle.putParcelable("data", (Parcelable) items.get(getAdapterPosition()));
                    Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
                }
            });
        }
    }
}
