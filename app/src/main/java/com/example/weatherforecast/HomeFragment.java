package com.example.weatherforecast;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecast.databinding.FragmentHomeBinding;
import com.example.weatherforecast.model.IdApiCall;
import com.example.weatherforecast.model.ItemRow;
import com.example.weatherforecast.view.ItemAdapter;
import com.example.weatherforecast.viewmodel.WeatherApiService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;

    private ItemAdapter itemAdapter;
    private ArrayList<ItemRow> items;
    private WeatherApiService apiService;
    private static IdApiCall mIdApiCall;

    double longitude;
    double latitude;
    LocationManager lm;
    Location location;
    private int REQUEST_LOCATION = 1;

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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // lay location hien tai

//        if (ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getContext(),
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(
//                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                            android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    REQUEST_LOCATION);
//        } else {
//            Log.e("DB", "PERMISSION GRANTED");
//        }
//        lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        longitude = location.getLongitude();
//        latitude = location.getLatitude();
//        for (int i = 0; i < 20; i++) {
//            Log.e("VI DO = ", longitude + "");
//            Log.e("BIEN DO = ", latitude + "");
//        }
//            final LocationListener locationListener = new LocationListener() {
//                public void onLocationChanged(Location location) {
//                    longitude = location.getLongitude();
//                    latitude = location.getLatitude();
//                }
//            };
//
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);


        //De nghi tai` dung FragmentBinding
        //ta da tao FragmentBinding binding o tren
        items = new ArrayList<ItemRow>();
        itemAdapter = new ItemAdapter(items,getActivity());
        //khoi tao gia tri thi de phia ngoai AsyncTask
        //Tim gia tri thi tim trong AsyncTask
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mIdApiCall = new IdApiCall();
                apiService = new WeatherApiService();
                apiService.getAPI()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<IdApiCall>() {
                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull IdApiCall idApiCall) {
                                //mIdApiCall la toan bo thong tin lay ve tu API
                                mIdApiCall = idApiCall;
                                for(int i = 0;i<mIdApiCall.getCnt();i++){
                                    ItemRow nitem = new ItemRow(mIdApiCall.getListItem().get(i).getDtTxt().substring(0,mIdApiCall.getListItem().get(i).getDtTxt().indexOf(" ")),
                                            mIdApiCall.getListItem().get(i).getDtTxt().substring(mIdApiCall.getListItem().get(i).getDtTxt().indexOf(" "),mIdApiCall.getListItem().get(i).getDtTxt().length()),
                                            mIdApiCall.getListItem().get(i).getMain().getHumidity()+"",
                                            mIdApiCall.getListItem().get(i).getMain().getTemp()+"",
                                            mIdApiCall.getListItem().get(i).getMain().getPressure()+"",
                                            mIdApiCall.getListItem().get(i).getRain().get3h()+"",
                                            mIdApiCall.getListItem().get(i).getVisibility());
                                    items.add(nitem);
                                }

                                //Moi chinh sua lien quan toi xml dung trong nay
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        binding.rvHours.setAdapter(itemAdapter);
                                        binding.rvHours.setHasFixedSize(true);
                                        binding.rvHours.setLayoutManager(new GridLayoutManager(getActivity(),1));
                                        onCreateHeadFragment();
                                        //setupAnim();
                                    }
                                });


                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                                if (e instanceof HttpException) {
                                    Log.e("Error", ((HttpException) e).message());
                                }
                                else if (e instanceof IOException) {
                                    Log.e("Error",e.toString());
                                }
                                else {
                                    Log.e("Error",e.toString());
                                }
                            }
                        });


            }
        });


            return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void onCreateHeadFragment() {
        DateFormat dateFormat = new SimpleDateFormat("E, HH:mm dd:MM:yyyy", new Locale("vi"));
        binding.timeTextView.setText(dateFormat.format(new Date(System.currentTimeMillis())));
        binding.statusTextView.setText(mIdApiCall.getListItem().get(0).getWeather().get(0).getDescription() );
        binding.iconStatusImageView.setImageResource(R.drawable.ic_rain);
        double nhietdo = mIdApiCall.getListItem().get(0).getMain().getTemp() - 273.15;
        binding.temperatureTextView.setText((int)nhietdo  + "");
        double nhietdoMax = mIdApiCall.getListItem().get(0).getMain().getTempMax() - 273.15;
        binding.temperatureUpTextView.setText((int)nhietdoMax  + "");
        double nhietdoMin = mIdApiCall.getListItem().get(0).getMain().getTempMin() - 273.15;
        binding.temperatureDownTextView.setText((int)nhietdoMin  + "");
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

//                    longitude = location.getLongitude();
//                    latitude = location.getLatitude();
//                    for(int i = 0; i<20; i++) {
//                        Log.e("VI DO = ", longitude + "");
//                        Log.e("BIEN DO = ", latitude + "");
//                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
