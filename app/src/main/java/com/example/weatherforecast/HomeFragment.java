
package com.example.weatherforecast;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.model.itemRow;
import com.example.weatherforecast.view.ItemAdapter;
import com.example.weatherforecast.viewmodel.WeatherApiService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment {

    private TextView timeTextView;
    private TextView statusTextView;
    private ImageView statusImageView;
    private TextView temperatureTextView;
    private TextView temperatureUpTextView;
    private TextView temperatureDownTextView;

    private ItemAdapter itemAdapter;
    private ArrayList<itemRow> items;
    private WeatherApiService apiService;
    private RecyclerView rvHours;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemAdapter = new ItemAdapter(items, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        rvHours = view.findViewById(R.id.rv_hours);
        rvHours.setLayoutManager(new GridLayoutManager(getContext(), 1));
        fakeData1();
        setupAnim();

        items = new ArrayList<itemRow>();
        apiService = new WeatherApiService();
        rvHours.setAdapter(itemAdapter);
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

    private void setupAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                statusImageView,
                "translationY",
                statusImageView.getY() - 10, statusImageView.getY() + 10);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }
}