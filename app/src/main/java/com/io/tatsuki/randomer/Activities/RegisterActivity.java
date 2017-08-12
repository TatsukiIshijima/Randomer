package com.io.tatsuki.randomer.Activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.io.tatsuki.randomer.Events.ButtonEnableEvent;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Utils.KeyboardUtil;
import com.io.tatsuki.randomer.ViewModels.RegisterViewModel;
import com.io.tatsuki.randomer.databinding.ActivityRegisterBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 登録画面
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private ActivityRegisterBinding mBinding;
    private RegisterViewModel mRegisterViewModel;

    /**
     * 画面遷移のためのIntent発行
     * @param context
     * @return intent
     */
    public static Intent registerIntent(@NonNull Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mRegisterViewModel = new RegisterViewModel();
        mBinding.setRegisterViewModel(mRegisterViewModel);

        setViews();
        initButtonState();
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
        setSupportActionBar(mBinding.activityRegisterToolbar);
        // SeekBar
        mRegisterViewModel.setPasswordLength(8);
        mRegisterViewModel.setPasswordLengthTitle();
        // データバインディングを実行しないとリスナーがセットされない
        mBinding.executePendingBindings();
        mBinding.activityRegisterSeekbar.setOnSeekBarChangeListener(mRegisterViewModel.seekBarChangeListener());
        // EditText
        mBinding.activityRegisterTitleEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
        mBinding.activityRegisterUserIdEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
        mBinding.activityRegisterPasswordEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
    }

    /**
     * ボタンの初期化
     */
    private void initButtonState() {
        mBinding.activityRegisterSaveButton.setEnabled(false);
    }

    /**
     * ボタンの活性・非活性のイベント講読
     * @param event
     */
    @Subscribe
    public void subScribeButtonEnableEvent(ButtonEnableEvent event) {
        mBinding.activityRegisterSaveButton.setEnabled(event.isButtonFlag());
    }
}
