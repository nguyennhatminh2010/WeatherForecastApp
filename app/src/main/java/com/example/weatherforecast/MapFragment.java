package com.example.weatherforecast;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherforecast.model.api.IdApiCall;
import com.example.weatherforecast.viewmodel.WeatherApiService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private WeatherApiService apiService;
    private static IdApiCall mIdApiCall;
    private ArrayList<LatLng> viTriTp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mIdApiCall = new IdApiCall();
        apiService = new WeatherApiService();
        viTriTp = new ArrayList<LatLng>();
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync( this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.0678,106),6));

        //Da Nang
        viTriTp.add(new LatLng(16.07,108.22));
        //Ha noi
        viTriTp.add(new LatLng(21.02,105.84));
        //Sai Gon
        viTriTp.add(new LatLng(10.83,106.67));
        //Ca Mau
        viTriTp.add(new LatLng(9.09,105.08));
        //Nghe An
        viTriTp.add(new LatLng(19.33,104.83));
        //Ha GIang
        viTriTp.add(new LatLng(22.75,105));
        //Quang Binh
        viTriTp.add(new LatLng(17.5,106.33));
        //Gia Lai
        viTriTp.add(new LatLng(13.75,108.25));

        ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

        for( int index = 0;index<viTriTp.size();index++) {
            markerOptions.add(new MarkerOptions());
            markerOptions.get(index).position(viTriTp.get(index));
        }
        for(int index = 0;index<viTriTp.size();index++){
            apiService.getAPI(viTriTp.get(index).latitude,viTriTp.get(index).longitude)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<IdApiCall>() {
                        @Override
                        public void onSuccess(@NonNull IdApiCall idApiCall) {
                            mIdApiCall = idApiCall;
//                            Log.e("VI TRI TP : ", mIdApiCall.getCity().getCoord().getLat() + " : " + mIdApiCall.getCity().getCoord().getLon());
//                            for(int i = 0;i<viTriTp.size();i++){
//                                Log.e("Index " + i + " : ", viTriTp.get(i).latitude + " : " + viTriTp.get(i).longitude);
//                            }
                            int vitri = viTriTp.indexOf(new LatLng(mIdApiCall.getCity().getCoord().getLat(),mIdApiCall.getCity().getCoord().getLon()));
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(mIdApiCall.getListItem().get(0).getWeather().get(0).getMain().equals("Rain")) markerOptions.get(vitri).icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_rainy));
                                    else if(mIdApiCall.getListItem().get(0).getWeather().get(0).getMain().equals("Clouds")) markerOptions.get(vitri).icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_weather));
                                    else markerOptions.get(vitri).icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_sun));
                                    gMap.addMarker(markerOptions.get(vitri));
                                }
                            });

                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }
                    });


        }



    }
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}