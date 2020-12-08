package com.example.weatherforecast;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.weatherforecast.databinding.FragmentHomeBinding;
import com.example.weatherforecast.model.IdApiCall;
import com.example.weatherforecast.model.ListItem;
import com.example.weatherforecast.model.SampleApiCall;
import com.example.weatherforecast.model.SampleDatabase;
import com.example.weatherforecast.view.ItemAdapter;
import com.example.weatherforecast.viewmodel.SampleDao;
import com.example.weatherforecast.viewmodel.WeatherApiService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class SecondFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ItemAdapter itemAdapter;
    private WeatherApiService apiService;
    private static IdApiCall mIdApiCall;

    private SampleApiCall sampleApiCall;
    private SampleDatabase sampleDatabase;
    private SampleDao sampleDao;

    private double latitude=16.0678;
    private double longitude=108.2208;
    LocationManager lm;
    Location location;
    private int REQUEST_LOCATION = 1;
    private Disposable mDispose;

    public SecondFragment() {
        super(R.layout.fragment_home);
    }

    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date today = new Date(System.currentTimeMillis());
        itemAdapter = new ItemAdapter(new ArrayList<>(), today.getDate() + 1, getContext());
    }

    @Override
    public void onDestroyView() {
        if(mDispose!=null) mDispose.dispose();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                mIdApiCall = new IdApiCall();
                apiService = new WeatherApiService();
                sampleDatabase = SampleDatabase.getInstance(getActivity());
                sampleDao = sampleDatabase.sampleDao();
                List<SampleApiCall> sampleApiCalls = sampleDao.getAllSampleApiCall();

                boolean check = false;//Check : co can phai lay data moi tu api hay khong
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                //Nhan du lieu tu database
                if (sampleApiCalls.size() == 0)
                    check = true;
                else {
                    mIdApiCall = sampleApiCalls.get(sampleApiCalls.size() - 1).getIdApiCall();

                    //so sanh
                    if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(0, 4).equals(today.year + ""))
                        check = true;
                    else if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(5, 7).equals((today.month + 1) + ""))
                        check = true;
                    else if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(8, 10).equals(today.monthDay < 10 ? ("0" + today.monthDay) : (today.monthDay + "")))
                        check = true;
                    Log.e("Date : ", today.year + "-" + (today.month + 1) + "-" + today.monthDay + "-" + check);
                }
                if (check) {
                    mDispose = apiService.getAPI(latitude, longitude)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<IdApiCall>() {
                                @Override
                                public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull IdApiCall idApiCall) {
                                    //mIdApiCall la toan bo thong tin lay ve tu API
                                    mIdApiCall = idApiCall;

                                    AsyncTask.execute(new Runnable() {
                                        @Override
                                        public void run() {

                                            sampleDao.insertSampleApiCall(new SampleApiCall(mIdApiCall));
                                        }
                                    });

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Date today = new Date(System.currentTimeMillis());

                                            binding.rvHours.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                            itemAdapter = new ItemAdapter(mIdApiCall.getListItem(),today.getDate() + 1, getActivity());
                                            onCreateHeadFragment();
                                            binding.rvHours.setHasFixedSize(true);
                                            binding.rvHours.setAdapter(itemAdapter);
                                            itemAdapter.notifyDataSetChanged();
                                        }
                                    });

                                }

                                @Override
                                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                    if (e instanceof HttpException) {
                                        Log.e("Error", ((HttpException) e).message());
                                    } else if (e instanceof IOException) {
                                        Log.e("Error", e.toString());
                                    } else {
                                        Log.e("Error", e.toString());
                                    }
                                }
                            });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Date today = new Date(System.currentTimeMillis());

                            binding.rvHours.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                            itemAdapter = new ItemAdapter(mIdApiCall.getListItem(),today.getDate() + 1, getActivity());
                            onCreateHeadFragment();
                            binding.rvHours.setHasFixedSize(true);
                            binding.rvHours.setAdapter(itemAdapter);
                            itemAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAnim();
    }

    private void onCreateHeadFragment() {
        DateFormat dateFormat = new SimpleDateFormat("E, HH:mm dd/MM/yyyy", new Locale("vi"));
        binding.timeTextView.setText(dateFormat.format(new Date(System.currentTimeMillis())));
        binding.statusTextView.setText(mIdApiCall.getListItem().get(8).getWeather().get(0).getDescription());
        double nhietdo = mIdApiCall.getListItem().get(8).getMain().getTemp() - 273.15;
        binding.temperatureTextView.setText((int) nhietdo + "");
        double nhietdoMax = mIdApiCall.getListItem().get(8).getMain().getTempMax() - 273.15;
        binding.temperatureUpTextView.setText((int) nhietdoMax + "");
        double nhietdoMin = mIdApiCall.getListItem().get(8).getMain().getTempMin() - 273.15;
        binding.temperatureDownTextView.setText((int) nhietdoMin + "");
        switch (mIdApiCall.getListItem().get(8).getWeather().get(0).getMain()) {
            case "Snow": {
                binding.iconStatusImageView.setImageResource(R.drawable.ic_snowy);
                binding.textView.setText("Trời mưa đấy ra đường nhớ mang ô nhé");
                break;
            }
            case "Rain": {
                binding.iconStatusImageView.setImageResource(R.drawable.ic_rainy);
                binding.textView.setText("Trời mưa đấy ra đường nhớ mang ô nhé");
                break;
            }
            case "Sunny": {
                binding.iconStatusImageView.setImageResource(R.drawable.ic_sun);
                binding.textView.setText("Trời nắng đấy ra đường nhớ mang nón nhé");
                break;
            }
            default:
                binding.iconStatusImageView.setImageResource(R.drawable.ic_weather);
                binding.textView.setText("Trời nắng đấy ra đường nhớ mang nón nhé");
        }
    }

    private void setupAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                binding.iconStatusImageView,
                "translationY",
                binding.iconStatusImageView.getY() - 10, binding.iconStatusImageView.getY() + 10);
        animator.setDuration(2000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                binding.temperatureTextView,
                "translationX",
                binding.temperatureDownTextView.getX() - 10, binding.temperatureDownTextView.getX() + 10);
        animator1.setDuration(2000);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}