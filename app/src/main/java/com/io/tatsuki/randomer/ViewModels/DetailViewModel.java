package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.Activities.RegisterActivity;
import com.io.tatsuki.randomer.Events.TransitionEvent;
import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 詳細画面 ViewModel
 */

public class DetailViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<String> mPassword = new ObservableField<>();
    public ObservableField<String> mUrl = new ObservableField<>();
    private Item mItem;

    public DetailViewModel(Item item) {
        this.mItem = item;
    }

    public void setTitle() {
        mTitle.set(mItem.getMTitle());
    }

    public void setUserId() {
        mUserId.set(mItem.getMUserId());
    }

    public void setPassword() {
        mPassword.set(mItem.getMPassword());
    }

    public void setUrl() {
        mUrl.set(mItem.getMUrl());
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
                // ホーム画面に遷移（ActivityForResult?）
                break;
        }
    }
}
