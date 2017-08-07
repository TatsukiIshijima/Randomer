package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.io.tatsuki.randomer.Adapters.ItemAdapter;
import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.ViewModels.HomeViewModel;
import com.io.tatsuki.randomer.databinding.ActivityHomeBinding;

import java.util.ArrayList;

/**
 *  ホーム画面
 */
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding mBinding;
    private HomeViewModel mHomeViewModel;

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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mHomeViewModel = new HomeViewModel();
        mBinding.setHomeViewModel(mHomeViewModel);

        setViews();
        setListAndAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // SearchView
        SearchView mSearchView = (SearchView) mBinding.activityHomeToolbar.getMenu().findItem(R.id.menu_action_search).getActionView();
        mSearchView.setOnQueryTextListener(mHomeViewModel.queryTextListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 各Viewの設定
     */
    private void setViews() {
        // Toolbar
        setSupportActionBar(mBinding.activityHomeToolbar);

        // NavigationDrawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                               mBinding.activityHomeDrawerLayout,
                                                                 mBinding.activityHomeToolbar,
                                                                 R.string.app_name,
                                                                 R.string.app_name);
        mBinding.activityHomeDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mBinding.activityHomeNavigation.setNavigationItemSelectedListener(mHomeViewModel.selectedListener);
    }

    /**
     * リスト表示の設定
     */
    private void setListAndAdapter() {
        // ダミーデータ
        ArrayList<Item> items = new ArrayList<>();
        for (int i=0; i < 20; i++) {
            Item item = new Item();
            item.setMTitle("Title" + i);
            items.add(item);
        }

        mBinding.activityHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ItemAdapter mItemAdapter = new ItemAdapter(getApplicationContext(), items);
        mBinding.activityHomeRecyclerView.setAdapter(mItemAdapter);
    }
}
