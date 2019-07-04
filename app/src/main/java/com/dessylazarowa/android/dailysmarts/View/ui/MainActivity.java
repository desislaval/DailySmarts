package com.dessylazarowa.android.dailysmarts.View.ui;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.dessylazarowa.android.dailysmarts.R;
import com.dessylazarowa.android.dailysmarts.adapters.ViewPagerAdapter;
import com.dessylazarowa.android.dailysmarts.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements MyQuotesFragment.OnFragmentInteractionListener, DailyQuoteFragment.OnFragmentInteractionListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setUpToolbar();
        setUpViewPager();
        checkInternetConnection();
    }


    private void setUpToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.app_name);
    }

    private void setUpViewPager() {
        binding.viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void checkInternetConnection(){
        if(!hasInternetConnection()){
            Snackbar snackbar = Snackbar
                    .make(binding.coordinatorLayout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    private boolean hasInternetConnection() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return connected = true;
        } else {
            return connected = false;
        }
    }
}