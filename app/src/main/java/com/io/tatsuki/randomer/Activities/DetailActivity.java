package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.ViewModels.DetailViewModel;
import com.io.tatsuki.randomer.databinding.ActivityDetailBinding;

/**
 * 詳細画面
 */
public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String ITEM_KEY = "ITEM_KEY";

    private ActivityDetailBinding mBinding;
    private DetailViewModel mDetailViewModel;

    /**
     * 画面遷移のためのIntent発行
     * @param activity
     * @param item
     * @return intent
     */
    public static Intent detailIntent(@NonNull Activity activity, Item item) {
        Intent intent = new Intent(activity, DetailActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(ITEM_KEY, item);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mDetailViewModel = new DetailViewModel();
        mBinding.setDetailViewModel(mDetailViewModel);

        setViews();
    }

    /**
     * 各Viewの設定
     */
    private void setViews() {
        // Toolbar
        setSupportActionBar(mBinding.activityDetailToolbar);
    }
}
