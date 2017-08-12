package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.R;

/**
 * 登録画面のViewModel
 */

public class RegisterViewModel {

    private static final String TAG = RegisterViewModel.class.getSimpleName();

    public ObservableField<String> mCategory = new ObservableField<>();
    public ObservableField<String> mTitle = new ObservableField<>();
    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<String> mPassword = new ObservableField<>();
    public ObservableField<String> mUrl = new ObservableField<>();
    public ObservableField<String > mPasswordLengthTitle = new ObservableField<>();
    public ObservableInt mPasswordLength = new ObservableInt();

    public void setCategory(String category) {
        mCategory.set(category);
    }

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

    public void setPasswordLengthTitle() {
        mPasswordLengthTitle.set("パスワード桁数 : " + mPasswordLength.get());
    }

    /**
     * 保存ボタンの活性・非活性の切り替え
     * @return
     */
    public boolean getSaveButtonEnabled() {
        if (mTitle.get() != null && mUserId.get() != null && mPassword.get() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ボタンイベント
     * @param view
     */
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.activity_register_add_button:
                Log.d(TAG, "Add Button Clicked");
                break;
            // 生成ボタン
            case R.id.activity_register_generate_button:
                Log.d(TAG, "Generate Button Clicked");
                break;
            // 保存ボタン
            case R.id.activity_register_save_button:
                Log.d(TAG, "Save Button Clicked");
                break;
            default:
                break;
        }
    }
}
