package com.io.tatsuki.randomer.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.io.tatsuki.randomer.Events.TransitionEvent;
import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.ViewModels.DetailViewModel;
import com.io.tatsuki.randomer.databinding.ActivityDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 詳細画面
 */
public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String ITEM_KEY = "ITEM_KEY_DETAIL";

    private ActivityDetailBinding mBinding;
    private DetailViewModel mDetailViewModel;
    private Item mItem;

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

        mItem = getItem();
        mDetailViewModel = new DetailViewModel(mItem);
        mBinding.setDetailViewModel(mDetailViewModel);

        setViews();
        setValue();
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
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 各Viewの設定
     */
    private void setViews() {
        // Toolbar
        setSupportActionBar(mBinding.activityDetailToolbar);
    }

    /**
     * 受け渡しされたItemを受け取る
     * @return  item
     */
    private Item getItem() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Item item = (Item) bundle.getSerializable(ITEM_KEY);
            return item;
        } else {
            return null;
        }
    }

    /**
     * 遷移後の受け取った各値の設定
     */
    private void setValue() {
        if (mItem != null) {
            mDetailViewModel.setTitle();
            mDetailViewModel.setUserId();
            mDetailViewModel.setPassword();
            mDetailViewModel.setUrl();
            // TODO:以下が反映されない
            mBinding.activityDetailToolbar.setTitle(mItem.getMCategory());
        }
    }

    /**
     * 画面遷移のためのイベント講読
     * @param event
     */
    @Subscribe
    public void subScribeTransitionEvent(TransitionEvent event) {
        switch (event.getTransitionFlag()) {
            case TransitionEvent.TRANS_TO_REGISTER_FLAG:
                Intent intent = RegisterActivity.registerIntent(this, mItem);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
