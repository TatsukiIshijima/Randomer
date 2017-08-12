package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.io.tatsuki.randomer.Events.ButtonEnableEvent;
import com.io.tatsuki.randomer.R;

import org.greenrobot.eventbus.EventBus;

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
    public ObservableBoolean mNumberToggle = new ObservableBoolean();
    public ObservableBoolean mUpperToggle = new ObservableBoolean();
    public ObservableBoolean mLowerToggle = new ObservableBoolean();
    public ObservableBoolean mSymbolToggle = new ObservableBoolean();

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

    public void setPasswordLength(int length) {
        mPasswordLength.set(length);
    }

    public void setPasswordLengthTitle() {
        mPasswordLengthTitle.set("パスワード桁数 : " + mPasswordLength.get());
    }

    /**
     * 保存ボタンの活性・非活性の切り替え
     * @return
     */
    private void getSaveButtonEnabled() {
        if (mTitle.get() != null && mUserId.get() != null && mPassword.get() != null) {
            EventBus.getDefault().post(new ButtonEnableEvent(true));
        } else {
            EventBus.getDefault().post(new ButtonEnableEvent(false));
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
                /* データバインディングテスト
                Log.d(TAG,
                                mTitle.get() + "\n" +
                                mUserId.get() + "\n" +
                                mPassword.get() + " : " + mPasswordLength.get() + "\n" +
                                mNumberToggle.get() + "\n" +
                                mUpperToggle.get() + "\n" +
                                mLowerToggle.get() + "\n" +
                                mSymbolToggle.get());
                */
                break;
            // 保存ボタン
            case R.id.activity_register_save_button:
                Log.d(TAG, "Save Button Clicked");
                break;
            default:
                break;
        }
    }

    /**
     * EditTextの編集イベント
     * @return
     */
    public TextWatcher textChangeLister() {
        TextWatcher textWatcher = new TextWatcher() {
            // 変更前
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }
            // 変更中
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                getSaveButtonEnabled();
            }
            // 変更後
            @Override
            public void afterTextChanged(Editable editable) {
                getSaveButtonEnabled();
            }
        };
        return textWatcher;
    }

    /**
     * SeekBarのイベント
     * @return
     */
    public SeekBar.OnSeekBarChangeListener seekBarChangeListener() {
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            // ドラック中
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d(TAG, "onProgressChanged");
                setPasswordLength(i);
                setPasswordLengthTitle();
            }
            // タップ時
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch");
                setPasswordLengthTitle();
            }
            // タップ終了時
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch");
                setPasswordLengthTitle();
            }
        };
        return seekBarChangeListener;
    }
}
