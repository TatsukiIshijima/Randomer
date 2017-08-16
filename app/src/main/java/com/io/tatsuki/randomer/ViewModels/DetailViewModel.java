package com.io.tatsuki.randomer.ViewModels;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.LocalAccess;
import com.io.tatsuki.randomer.Repositories.db.Item;

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
    private Item mItem;
    private LocalAccess mLocalAccess;

    public DetailViewModel(Context context, Item item) {
        this.mContext = context;
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
        }
    }

    /**
     * 削除
     */
    public void delete(Item item) {
        mLocalAccess.delete(item);
    }
}
