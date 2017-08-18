package com.io.tatsuki.randomer.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.LocalAccess;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.databinding.ActivityDetailBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * 詳細画面 ViewModel
 */

public class DetailViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    public ObservableField<String> mCategory = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<String> mPassword = new ObservableField<>();
    public ObservableField<String> mUrl = new ObservableField<>();
    private Context mContext;
    private ActivityDetailBinding mBinding;
    private Item mItem;
    private LocalAccess mLocalAccess;

    public DetailViewModel(Context context, ActivityDetailBinding binding, Item item) {
        this.mContext = context;
        this.mBinding = binding;
        this.mItem = item;
        mLocalAccess = new LocalAccess(context);
        mLocalAccess.setupDB();
    }

    public void setCategory() {
        mCategory.set(mItem.getCategory());
    }

    public void setTitle() {
        mTitle.set(mItem.getTitle());
    }

    public void setUserId() {
        mUserId.set(mItem.getUsetId());
    }

    public void setPassword() {
        mPassword.set(mItem.getPassword());
    }

    public void setUrl() {
        mUrl.set(mItem.getUrl());
    }

    /**
     * ボタンイベント
     * @param view
     */
    public void onClickButton(View view) {
        switch (view.getId()) {
            // 編集ボタン
            case R.id.activity_detail_edit_button:
                Log.d(TAG, "Edit Button Clicked");
                // 登録画面に遷移
                EventBus.getDefault().post(new TransitionEvent(TransitionEvent.TRANS_TO_REGISTER_FLAG));
                break;
            // 削除ボタン
            case R.id.activity_detail_delete_button:
                Log.d(TAG, "Delete Button Clicked");
                // ホーム画面に遷移
                EventBus.getDefault().post(new TransitionEvent(TransitionEvent.TRANS_TO_HOME_FLAG));
                break;
            // URLテキスト
            case R.id.activity_detail_url_text:
                Log.d(TAG, "URL Clicked");
                if (mUrl.get() != null) {
                    openWebPage(mUrl.get());
                }
                break;
            // チェックボタン
            case R.id.activity_detail_check_box:
                Log.d(TAG, "Check Box Clicked");
                setPasswordVisible();
                break;
        }
    }

    /**
     * 削除
     */
    public void delete(Item item) {
        mLocalAccess.delete(item);
    }

    /**
     * 指定したURLを開く
     * @param url
     */
    public void openWebPage(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    /**
     * パスワードの表示非表示の切り替え
     */
    public void setPasswordVisible() {
        if (mBinding.activityDetailCheckBox.isChecked()) {
            mBinding.activityDetailPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            mBinding.activityDetailPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
