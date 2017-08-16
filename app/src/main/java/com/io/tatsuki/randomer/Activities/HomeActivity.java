package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.io.tatsuki.randomer.Adapters.ItemAdapter;
import com.io.tatsuki.randomer.Events.TransitionEvent;
import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Utils.ActivityForResultConstant;
import com.io.tatsuki.randomer.ViewModels.HomeViewModel;
import com.io.tatsuki.randomer.databinding.ActivityHomeBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 *  ホーム画面
 */
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private ActivityHomeBinding mBinding;
    private HomeViewModel mHomeViewModel;
    private ItemAdapter mItemAdapter;
    private ArrayList<Item> mItemList;

    /**
     * 画面遷移のためのIntent発行
     * @param context
     * @return intent
     */
    public static Intent homeIntent(@NonNull Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mHomeViewModel = new HomeViewModel(this);
        mBinding.setHomeViewModel(mHomeViewModel);
        mItemList = mHomeViewModel.getItemList();

        setViews();
        setListAndAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult : RequestCode : " + requestCode + " ResultCode : " + resultCode);
        switch (requestCode) {
            case ActivityForResultConstant.DELETE_REQUEST:
                if (resultCode == RESULT_OK) {
                    updateAdapter();
                    Snackbar.make(mBinding.activityHomeCoordinateLayout, "削除しました。", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case ActivityForResultConstant.REGISTER_REQUEST:
                if (resultCode == RESULT_OK) {
                    updateAdapter();
                    Snackbar.make(mBinding.activityHomeCoordinateLayout, "保存しました。", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // SearchView
        SearchView mSearchView = (SearchView) mBinding.activityHomeToolbar.getMenu().findItem(R.id.menu_action_search).getActionView();
        mSearchView.setOnQueryTextListener(mHomeViewModel.queryTextListener());
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
        mBinding.activityHomeNavigation.setNavigationItemSelectedListener(mHomeViewModel.itemSelectedListener());
    }

    /**
     * リスト表示の設定
     */
    private void setListAndAdapter() {
        mBinding.activityHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mItemAdapter = new ItemAdapter(getApplicationContext());
        mItemAdapter.setItemList(mHomeViewModel.getItemList());
        mBinding.activityHomeRecyclerView.setAdapter(mItemAdapter);
    }

    /**
     * アダプターの更新
     */
    private void updateAdapter() {
        mItemAdapter.setItemList(mHomeViewModel.getItemList());
        mItemAdapter.notifyDataSetChanged();
    }

    /**
     * 画面遷移のためのイベント講読
     * @param event
     */
    @Subscribe
    public void subScribeTransitionEvent(TransitionEvent event) {
        switch (event.getTransitionFlag()) {
            case TransitionEvent.TRANS_TO_DETAIL_FLAG:
                Intent detailIntent = DetailActivity.detailIntent(this, event.getItem());
                startActivityForResult(detailIntent, ActivityForResultConstant.DELETE_REQUEST);
                break;
            case TransitionEvent.TRANS_TO_REGISTER_FLAG:
                Intent registerIntent = RegisterActivity.registerIntent(this);
                startActivityForResult(registerIntent, ActivityForResultConstant.REGISTER_REQUEST);
                break;
            default:
                break;
        }
    }
}
