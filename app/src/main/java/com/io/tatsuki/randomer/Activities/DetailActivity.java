package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Context;
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
     * @param context
     * @param item
     * @return intent
     */
    public static Intent detailIntent(@NonNull Context context, Item item) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        setValue();
    }

    /**
     * 各Viewの設定
     */
    private void setViews() {
        // Toolbar
        setSupportActionBar(mBinding.activityDetailToolbar);
    }

    /**
     * 遷移後の受け取った各値の設定
     */
    private void setValue() {
        Bundle bundle = getIntent().getExtras();
        Item item = (Item) bundle.getSerializable(ITEM_KEY);
        mDetailViewModel.setTitle(item.getMTitle());
        mDetailViewModel.setUserId(item.getMUserId());
        mDetailViewModel.setPassword(item.getMPassword());
        mDetailViewModel.setUrl(item.getMUrl());
        // TODO:以下が反映されない
        mBinding.activityDetailToolbar.setTitle(item.getMCategory());
    }
}
