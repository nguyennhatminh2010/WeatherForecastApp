package com.example.weatherforecast.view;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherforecast.topdrawer.FifthFragment;
import com.example.weatherforecast.topdrawer.FourthFragment;
import com.example.weatherforecast.topdrawer.HomeFragment;
import com.example.weatherforecast.topdrawer.SecondFragment;
import com.example.weatherforecast.topdrawer.ThirdFragment;

import java.util.Calendar;
import java.util.Date;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            case 3:
                return new FourthFragment();
            case 4:
                return new FifthFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        Date dt = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(dt);

        Log.e("Date: ", dt.getDate() + "/" + dt.getMonth());

        String title = "";
        switch (position) {
            case 0:
                title = "Today";
                break;
            case 1:
                c.add(Calendar.DATE, 1);
                dt = c.getTime();
                title = (dt.getDate() + "/" + (dt.getMonth()+1));
                break;
            case 2:
                c.add(Calendar.DATE, 2);
                dt = c.getTime();
                title = (dt.getDate() + "/" + (dt.getMonth()+1));
                break;
            case 3:
                c.add(Calendar.DATE, 3);
                dt = c.getTime();
                title = (dt.getDate() + "/" + (dt.getMonth()+1));
                break;
            case 4:
                c.add(Calendar.DATE, 4);
                dt = c.getTime();
                title = (dt.getDate() + "/" + (dt.getMonth()+1));
                break;
        }
        return title;
    }
}
