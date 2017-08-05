package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.ViewModels.HomeViewModel;
import com.io.tatsuki.randomer.databinding.ActivityHomeBinding;

/**
 *  ホーム画面
 */
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;

    /**
     * 画面遷移のためのIntent発行
     * @param activity
     * @return intent
     */
    public static Intent homeIntent(@NonNull Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        homeViewModel = new HomeViewModel();
        binding.setHomeViewModel(homeViewModel);

        setViews();
    }

    /**
     * 各Viewの設定
     */
    private void setViews() {
        // Toolbar
        setSupportActionBar(binding.activityHomeToolbar);
        // NavigationDrawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                 binding.activityHomeDrawerLayout,
                                                                 binding.activityHomeToolbar,
                                                                 R.string.app_name,
                                                                 R.string.app_name);
        binding.activityHomeDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
