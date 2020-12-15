package com.example.weatherforecast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherforecast.databinding.FragmentDetailBinding;
import com.example.weatherforecast.model.inforapi.ListItem;

public class DetailFragment extends Fragment {

    private ListItem item;

    FragmentDetailBinding binding;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item = (ListItem) getArguments().getSerializable("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String hour  = item.getDtTxt().substring(item.getDtTxt().indexOf(" ")).substring(0,3).concat("h");
        binding.textviewTime.setText(hour + "");
        double temperature = item.getMain().getTemp() - 273.15;
        binding.textviewTemp.setText((int)temperature + "°C");
        String description = item.getWeather().get(0).getDescription();
        binding.textviewDescription.setText(description);
        binding.textviewHumidity.setText(item.getMain().getHumidity() + "");
        binding.textviewWind.setText(item.getWind().getSpeed() + "");
        binding.textviewVisibility.setText(item.getVisibility() + "");
        double realfeel = item.getMain().getFeelsLike() - 273.15;
        binding.textviewRealf.setText((int) realfeel + "");

    }
}