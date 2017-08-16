package com.io.tatsuki.randomer.ViewModels;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.io.tatsuki.randomer.Events.TransitionEvent;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.LocalAccess;
import com.io.tatsuki.randomer.Repositories.db.Item;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * ホーム画面のViewModel
 */

public class HomeViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private Context mContext;
    private LocalAccess mLocalAccess;

    public HomeViewModel(Context context) {
        this.mContext = context;
        mLocalAccess = new LocalAccess(context);
        mLocalAccess.setupDB();
    }

    /**
     * ボタンタップイベント
     * @param view
     */
    public void onClickButton(View view) {
        switch (view.getId()) {
            // Floating Action Button
            case R.id.activity_home_fb:
                Log.d(TAG, "Floating Action Button Clicked");
                // 登録画面遷移
                EventBus.getDefault().post(new TransitionEvent(TransitionEvent.TRANS_TO_REGISTER_FLAG));
                break;
            default:
                break;
        }
    }

    /**
     * 検索テキストイベント
     */
    public SearchView.OnQueryTextListener queryTextListener() {
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit : " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange : " + newText);
                return false;
            }
        };
        return queryTextListener;
    };

    /**
     * ナビゲーション内メニューイベント
     */
    public NavigationView.OnNavigationItemSelectedListener itemSelectedListener() {
        NavigationView.OnNavigationItemSelectedListener itemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        };
        return itemSelectedListener;
    }

    /**
     * DBからアイテムリストの取得
     * @return itemArrayList
     */
    public List<Item> getItemList() {
        List<Item> itemList = mLocalAccess.fetchItemList();
        return itemList;
    }
}
