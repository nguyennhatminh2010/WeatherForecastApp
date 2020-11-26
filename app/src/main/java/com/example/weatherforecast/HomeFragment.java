package com.example.weatherforecast;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private TextView timeTextView;
    private TextView statusTextView;
    private ImageView statusImageView;
    private TextView temperatureTextView;
    private TextView temperatureUpTextView;
    private TextView temperatureDownTextView;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeTextView = view.findViewById(R.id.time_text_view);
        statusTextView = view.findViewById(R.id.status_text_view);
        statusImageView = view.findViewById(R.id.icon_status_image_view);
        temperatureTextView = view.findViewById(R.id.temperature_text_view);
        temperatureUpTextView = view.findViewById(R.id.temperature_up_text_view);
        temperatureDownTextView = view.findViewById(R.id.temperature_down_text_view);
        fakeData1();
    }

    private void fakeData1() {
        DateFormat dateFormat = new SimpleDateFormat("E, HH:mm dd:MM:yyyy", new Locale("vi"));
        timeTextView.setText(dateFormat.format(new Date(System.currentTimeMillis())));
        statusTextView.setText("Nắng nhẹ");
        statusImageView.setImageResource(R.drawable.ic_sun);
        temperatureTextView.setText("28");
        temperatureUpTextView.setText("25°");
        temperatureDownTextView.setText("30°");
    }
}