package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;

/**
 * 詳細画面 ViewModel
 */

public class DetailViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();

    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<String> mPassword = new ObservableField<>();
    public ObservableField<String> mUrl = new ObservableField<>();

    public void setTitle(String title) {
        mTitle.set(title);
    }

    public void setUserId(String userId) {
        mUserId.set(userId);
    }

    public void setPassword(String password) {
        mPassword.set(password);
    }

    public void setUrl(String url) {
        mUrl.set(url);
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
                break;
            // 削除ボタン
            case R.id.activity_detail_delete_button:
                Log.d(TAG, "Delete Button Clicked");
                break;
        }
    }
}
