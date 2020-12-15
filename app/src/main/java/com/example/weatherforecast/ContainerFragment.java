package com.example.weatherforecast;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherforecast.databinding.FragmentContainerBinding;
import com.example.weatherforecast.topdrawer.HomeFragment;
import com.example.weatherforecast.view.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class ContainerFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentContainerBinding binding;
    private FragmentActivity myContext;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public ContainerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    public static ContainerFragment newInstance(String param1, String param2) {
        ContainerFragment fragment = new ContainerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContainerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        myContext.getSupportFragmentManager().beginTransaction().replace(R.id.view_pager,
                new HomeFragment()).commit();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(myContext.getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}