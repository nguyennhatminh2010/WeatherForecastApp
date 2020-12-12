package com.example.weatherforecast.view;

import android.app.Activity;
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
import androidx.navigation.ActivityNavigator;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.MainActivity;
import com.example.weatherforecast.R;
import com.example.weatherforecast.model.ListItem;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    ArrayList<ListItem> copyItems = new ArrayList<ListItem>();
    Context context;
    private int fromIndex = 0;
    private int dateStart = 0;

    public ItemAdapter(ArrayList<ListItem> items, int dateStart, Context context) {
        this.items = items;
        this.context = context;
        this.dateStart = dateStart;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ListItem item : items) {
            try {
                Log.e("xxxxxxxxxxxxxxxxxxxxx", dateStart + "---" + formatter.parse(item.getDtTxt()).getDate());
                if (dateStart == formatter.parse(item.getDtTxt()).getDate()) {
                    fromIndex = items.indexOf(item);
                }
                break;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Log.e("length: ", items.size() + "");
        Log.e("Spile","from: " + fromIndex);
        this.copyItems.clear();
        for (int i = fromIndex; i < items.size(); i++) {
            this.copyItems.add(this.items.get(i));
        }
        Log.e("lengthhh: ", copyItems.size() + "");

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
        return 8;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView txtHour, txtDay, txtHumidity, txtTemperature, txtRealFeel, txtDroplets;
        ImageView imgDrop;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
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
                    bundle.putSerializable("data", (Serializable) copyItems.get(getAdapterPosition()));
                    Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
                }
            });
        }
    }
}
