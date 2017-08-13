package com.io.tatsuki.randomer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.io.tatsuki.randomer.Events.ButtonEvent;
import com.io.tatsuki.randomer.R;
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
        // EditText
        mBinding.activityRegisterTitleEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
        mBinding.activityRegisterUserIdEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
        mBinding.activityRegisterPasswordEdit.addTextChangedListener(mRegisterViewModel.textChangeLister());
        // SeekBar
        mRegisterViewModel.setPasswordLength(8);
        mRegisterViewModel.setPasswordLengthTitle();
        // データバインディングを実行しないとリスナーがセットされない
        mBinding.executePendingBindings();
        mBinding.activityRegisterSeekbar.setOnSeekBarChangeListener(mRegisterViewModel.seekBarChangeListener());
    }

    /**
     * ボタンの初期化
     */
    private void initButtonState() {
        mBinding.activityRegisterSaveButton.setEnabled(false);
        mBinding.activityRegisterNumberToggle.setChecked(true);
        mBinding.activityRegisterUpperToggle.setChecked(true);
        mBinding.activityRegisterLowerToggle.setChecked(true);
        mBinding.activityRegisterSymbolToggle.setChecked(true);
    }

    /**
     * カテゴリーの追加のアラートの表示
     */
    private void showAddAlert() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alert_add_category, (ViewGroup) findViewById(R.id.alert_add_category_linear_layout));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("カテゴリー追加");
        builder.setView(view);
        builder.setPositiveButton("追加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
     * ボタンの活性・非活性のイベント講読
     * @param event
     */
    @Subscribe
    public void subScribeButtonEvent(ButtonEvent event) {
        Log.d(TAG, "FLAG : " + event.getButtonFlag());
        switch (event.getButtonFlag()) {
            case ButtonEvent.generateButtonFlag:
                Log.d(TAG, "subScribeButtonEvent : Generate");
                mBinding.activityRegisterGenerateButton.setEnabled(event.isButtonState());
                break;
            case ButtonEvent.saveButtonFlag:
                Log.d(TAG, "subScribeButtonEvent : Save");
                mBinding.activityRegisterSaveButton.setEnabled(event.isButtonState());
                break;
            case ButtonEvent.addButtonFlag:
                showAddAlert();
                break;
            default:
                break;
        }
    }
}
