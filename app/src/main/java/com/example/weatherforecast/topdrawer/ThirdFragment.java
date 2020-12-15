package com.example.weatherforecast.topdrawer;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.weatherforecast.R;
import com.example.weatherforecast.databinding.FragmentHomeBinding;
import com.example.weatherforecast.model.api.IdApiCall;
import com.example.weatherforecast.model.database.SampleApiCall;
import com.example.weatherforecast.model.database.SampleDatabase;
import com.example.weatherforecast.topdrawer.HomeFragment;
import com.example.weatherforecast.view.ItemAdapter;
import com.example.weatherforecast.viewmodel.SampleDao;
import com.example.weatherforecast.viewmodel.WeatherApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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

import static android.content.Context.LOCATION_SERVICE;

public class ThirdFragment extends Fragment {

    private FragmentHomeBinding binding;

    private ItemAdapter itemAdapter;
    private WeatherApiService apiService;
    private static IdApiCall mIdApiCall;

    private SampleApiCall sampleApiCall;
    private SampleDatabase sampleDatabase;
    private SampleDao sampleDao;

    private double latitude ;
    private double longitude ;
    private FusedLocationProviderClient client;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location loc;
    private boolean checkGPS;
    private boolean checkNetwork;

    private int REQUEST_LOCATION = 1;
    private Disposable mDispose;

    public ThirdFragment() {
        super(R.layout.fragment_home);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        itemAdapter = new ItemAdapter(new ArrayList<>(), today.monthDay+2, getContext());
    }

    @Override
    public void onDestroyView() {
        if (mDispose != null) mDispose.dispose();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //Khoi tao location client
        //Lay current location
        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Run app
            Log.e("Location ", "1 " + ":" + " A");
            getCurrentLocation();
        }else{
            Log.e("Location ", "1 " + ":" + " B");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        Log.e("Location ", latitude + ":" + longitude);


        //Vi viec lay current location delay vai giay nen phai dung thread de hoan tat viec lay truoc khi tao UI
        new CountDownTimer(3000,100){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("Location ", latitude + ":" + longitude);
                        mIdApiCall = new IdApiCall();
                        apiService = new WeatherApiService();
                        sampleDatabase = SampleDatabase.getInstance(getActivity());
                        sampleDao = sampleDatabase.sampleDao();
                        List<SampleApiCall> sampleApiCalls = sampleDao.getSpecificSampleApiCall("yourplace");



                        boolean check = false;//Check : co can phai lay data moi tu api hay khong
                        Time today = new Time(Time.getCurrentTimezone());
                        today.setToNow();
                        //Nhan du lieu tu database

                        if (sampleApiCalls.size() == 0)
                            check = true;
                        else {
//                    Log.e("Size of sample : ",sampleApiCalls.size() + "");
                            mIdApiCall = sampleApiCalls.get(sampleApiCalls.size() - 1).getIdApiCall();
//                    Log.e("Size of mIdApiCall : ", mIdApiCall.getListItem().size() + "");
                            //so sanh
                            if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(0, 4).equals(today.year + ""))
                                check = true;
                            else if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(5, 7).equals((today.month + 1) + ""))
                                check = true;
                            else if (!mIdApiCall.getListItem().get(0).getDtTxt().substring(8, 10).equals(today.monthDay < 10 ? ("0" + today.monthDay) : (today.monthDay + "")))
                                check = true;

                        }
                        Log.e("Date : ", today.year + "-" + (today.month + 1) + "-" + today.monthDay + "-" + check);
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
                                                    if(sampleApiCalls.size() == 0) {
                                                        Log.e("INSERT Successful","1231231231231");
                                                        sampleDao.insertSampleApiCall(new SampleApiCall("yourplace",mIdApiCall));
                                                    }
                                                    else {
                                                        Log.e("UPDATE Successful","1231231231231");
                                                        sampleDao.updateSampleApiCall( "yourplace", mIdApiCall);
                                                    }
                                                }
                                            });

                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    Time today = new Time(Time.getCurrentTimezone());
                                                    today.setToNow();
                                                    itemAdapter = new ItemAdapter(mIdApiCall.getListItem(), today.monthDay+2, getContext());

                                                    binding.rvHours.setLayoutManager(new GridLayoutManager(getActivity(), 1));
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
                        }
                        else {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            Time today = new Time(Time.getCurrentTimezone());
                                            today.setToNow();


                                            try {
                                                itemAdapter = new ItemAdapter(mIdApiCall.getListItem(), today.monthDay+2, getContext());
                                                binding.rvHours.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                                onCreateHeadFragment();
                                                binding.rvHours.setHasFixedSize(true);
                                                binding.rvHours.setAdapter(itemAdapter);
                                                itemAdapter.notifyDataSetChanged();
                                            }catch (Exception e){
                                                Log.e("Error : ", e.toString());
                                            }
                                        }
                                    });
                                }
                            });
                        }

                    }
                });
            }
        }.start();


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupAnim();
    }

    @SuppressLint("SetTextI18n")
    private void onCreateHeadFragment() {
        DateFormat dateFormat = new SimpleDateFormat("E, HH:mm dd/MM/yyyy", new Locale("vi"));
//        Log.e("Size ofmIdApiCalllate: ", mIdApiCall.getListItem().size() + "");
        binding.timeTextView.setText(dateFormat.format(new Date(System.currentTimeMillis())));
        binding.statusTextView.setText(mIdApiCall.getListItem().get(0).getWeather().get(0).getDescription().toUpperCase());
        double nhietdo = mIdApiCall.getListItem().get(0).getMain().getTemp() - 273.15;
        binding.temperatureTextView.setText((int) nhietdo + "");
        double nhietdoMax = mIdApiCall.getListItem().get(0).getMain().getTempMax() - 273.15;
        binding.temperatureUpTextView.setText((int) nhietdoMax + "");
        double nhietdoMin = mIdApiCall.getListItem().get(0).getMain().getTempMin() - 273.15;
        binding.temperatureDownTextView.setText((int) nhietdoMin + "");
        switch (mIdApiCall.getListItem().get(0).getWeather().get(0).getMain()) {
            case "Snow": {
                binding.iconStatusImageView.setImageResource(R.drawable.ic_snowy);
                binding.textView.setText("Trời đã đổ tuyết, sao em chưa đổ anh?");
                binding.fragmentHome.setBackgroundResource(R.drawable.rainy);
                break;
            }
            case "Rain": {
                binding.iconStatusImageView.setImageResource(R.drawable.icon_rain);
                binding.textView.setText("Thích thì mang ô, không thích thì mang ô");
                binding.fragmentHome.setBackgroundResource(R.drawable.rainy2);
                break;
            }
            case "Sunny": {
                binding.iconStatusImageView.setImageResource(R.drawable.icon_sun);
                binding.fragmentHome.setBackgroundResource(R.drawable.sunny);
                binding.textView.setText("Trời nắng đấy ra đường nhớ mang nón nhé");
                break;
            }
            case "Clear":{
                binding.iconStatusImageView.setImageResource(R.drawable.ic_clear);
                binding.fragmentHome.setBackgroundResource(R.drawable.clear);
                binding.textView.setText("Trời mây xanh, nắng lung linh");
                break;
            }
            case "Thunderstorm":{
                binding.iconStatusImageView.setImageResource(R.drawable.ic_rainy);
                binding.fragmentHome.setBackgroundResource(R.drawable.rainy);
                binding.textView.setText("Thời tiết xấu, cần ra ngoài thì nhớ mang ô theo nhé");
                break;
            }
            case "Drizzle":{
                binding.iconStatusImageView.setImageResource(R.drawable.ic_rainy);
                binding.fragmentHome.setBackgroundResource(R.drawable.rainy2);
                binding.textView.setText("Mưa lất phất, lòng não nề");
                break;
            }
            case "Clouds":{
                binding.iconStatusImageView.setImageResource(R.drawable.ic_cloud);
                binding.fragmentHome.setBackgroundResource(R.drawable.clear);
                binding.textView.setText("Mây mù giăng lối…đường vào tim em");
                break;
            }
            default:
                binding.iconStatusImageView.setImageResource(R.drawable.ic_weather);
                binding.textView.setText("Trời đong đầy gió, tim đầy yêu thương");
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && (grantResults.length > 0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else{
            Toast.makeText(getActivity(),
                    "Permission denied",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        //Khoi tao location manager
        mLocationManager = (LocationManager)getActivity()
                .getSystemService(LOCATION_SERVICE);
        Log.e("Location ", "2 " + ":" + " *");
        //Kiem tra dieu kien
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //When location services is enable
            //Get last location
            Log.e("Location ", "2 " + ":" + " A");
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>(){

                @Override
                public void onSuccess(Location location) {
                    Log.e("Location ", "2 " + ":" + " B");


                    if(location != null){
                        Log.e("Location ", "2 " + ":" + " B" + " A");
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }else{
                        Log.e("Location ", "2 " + ":" + " B" + " B");
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000);

                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Log.e("Location ", "2 " + ":" + " B B A");
                                super.onLocationResult(locationResult);
                                Location loc1 = locationResult.getLastLocation();
                                longitude = loc1.getLongitude();
                                latitude = loc1.getLatitude();
                            }
                        };
                        Log.e("Location ", "2 " + ":" + " C");
                        client.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }
                }
            });

        }else {
            Log.e("Location ", "2 " + ":" + " D");
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

}