package com.io.tatsuki.randomer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.Utils.ActivityForResultConstant;
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
    private static final String MESSAGE = "MESSAGE";

    private ActivityDetailBinding mBinding;
    private DetailViewModel mDetailViewModel;
    private Item mItem;
    private String mMessage;

    /**
     * 画面遷移のためのIntent発行
     * @param context
     * @param item
     * @return intent
     */
    public static Intent detailIntent(@NonNull Context context, Item item) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(ITEM_KEY, item);
        intent.putExtras(args);
        return intent;
    }

    public static Intent detailIntent(@NonNull Context context, Item item, String message) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(ITEM_KEY, item);
        intent.putExtras(args);
        intent.putExtra(MESSAGE, message);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        getItem();
        showMessage();
        mDetailViewModel = new DetailViewModel(this, mItem);
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
     * 受け渡しされたItem, Messageを受け取る
     */
    private void getItem() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mItem = (Item) bundle.getSerializable(ITEM_KEY);
            mMessage = bundle.getString(MESSAGE);
        }
    }

    /**
     * スナックバーでの編集メッセージ表示
     */
    private void showMessage() {
        if (mMessage != null) {
            Snackbar.make(mBinding.activityDetailCoordinateLayout, mMessage, Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 遷移後の受け取った各値の設定
     */
    private void setValue() {
        if (mItem != null) {
            mDetailViewModel.setCategory();
            mDetailViewModel.setTitle();
            mDetailViewModel.setUserId();
            mDetailViewModel.setPassword();
            mDetailViewModel.setUrl();
        }
    }

    /**
     * 削除の確認アラート表示
     */
    private void showDeleteAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("削除しますか？");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDetailViewModel.delete(mItem);
                setResult(RESULT_OK);
                finish();
            }
        });
        builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    /**
     * 画面遷移のためのイベント講読
     * @param event
     */
    @Subscribe
    public void subScribeTransitionEvent(TransitionEvent event) {
        switch (event.getTransitionFlag()) {
            case TransitionEvent.TRANS_TO_REGISTER_FLAG:
                Intent registerIntent = RegisterActivity.registerIntent(this, mItem, 1);
                startActivity(registerIntent);
                break;
            case TransitionEvent.TRANS_TO_HOME_FLAG:
                showDeleteAlert();
                break;
            default:
                break;
        }
    }
}
