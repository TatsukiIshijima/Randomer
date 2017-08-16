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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.io.tatsuki.randomer.Events.ButtonEvent;
import com.io.tatsuki.randomer.Events.TransitionEvent;
import com.io.tatsuki.randomer.Fragments.CategorySelectFragment;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.ViewModels.RegisterViewModel;
import com.io.tatsuki.randomer.databinding.ActivityRegisterBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 登録画面
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    public static final String ITEM_KEY = "ITEM_KEY_REGISTER";

    private ActivityRegisterBinding mBinding;
    private RegisterViewModel mRegisterViewModel;
    private ArrayAdapter mArrayAdapter;

    /**
     * 画面遷移のためのIntent発行
     * @param context
     * @return intent
     */
    public static Intent registerIntent(@NonNull Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }

    public static Intent registerIntent(@NonNull Context context, Item item) {
        Intent intent = new Intent(context, RegisterActivity.class);
        Bundle args = new Bundle();
        args.putSerializable(ITEM_KEY, item);
        intent.putExtras(args);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mRegisterViewModel = new RegisterViewModel(this);
        mBinding.setRegisterViewModel(mRegisterViewModel);

        setViews();
        initButtonState();
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
        // Spinner
        mRegisterViewModel.loadCategoryList();
        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mRegisterViewModel.getCategoryList());
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.activityRegisterSpinner.setAdapter(mArrayAdapter);
        mBinding.activityRegisterSpinner.setOnItemSelectedListener(mRegisterViewModel.spinnerItemSelected());
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
     * 値の設定
     */
    private void setValue() {
        Item item = getItem();
        if (item != null) {
            mBinding.activityRegisterSpinner.setSelection(mRegisterViewModel.getCategoryPosition(item.getCategory()));
            mRegisterViewModel.setTitle(item.getTitle());
            mRegisterViewModel.setUserId(item.getUsetId());
            mRegisterViewModel.setPassword(item.getPassword());
            mRegisterViewModel.setUrl(item.getUrl());
        }
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
        final View view = inflater.inflate(R.layout.alert_add_category, (ViewGroup) findViewById(R.id.alert_add_category_linear_layout));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("カテゴリー追加");
        builder.setView(view);
        builder.setPositiveButton("追加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) view.findViewById(R.id.alert_add_category_edit);
                if (editText.getText().length() != 0) {
                    for (String category : mRegisterViewModel.getCategoryList()) {
                        if (category.equals(editText.getText().toString())) {
                            Snackbar.make(mBinding.activityRegisterCoordinateLayout, "追加済みです。", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    mRegisterViewModel.addCategory(editText.getText().toString());
                    // リスト更新
                    mArrayAdapter.notifyDataSetChanged();
                    Snackbar.make(mBinding.activityRegisterCoordinateLayout, "追加しました。", Snackbar.LENGTH_SHORT).show();
                    // スピナーで選択済みになるよう設定
                    mBinding.activityRegisterSpinner.setSelection(mRegisterViewModel.getCategoryList().size());
                } else {
                    Snackbar.make(mBinding.activityRegisterCoordinateLayout, "追加できませんでした。", Snackbar.LENGTH_SHORT).show();
                }
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

    /**
     * 画面遷移イベント講読
     * @param event
     */
    @Subscribe
    public void subScribeTransitionEvent(TransitionEvent event) {
        switch (event.getTransitionFlag()) {
            case TransitionEvent.TRANS_TO_HOME_FLAG:
                // カテゴリーアイコンリスト表示
                Bundle bundle = new Bundle();
                bundle.putSerializable(ITEM_KEY, event.getItem());
                android.app.FragmentManager fragmentManager = getFragmentManager();
                CategorySelectFragment fragment = new CategorySelectFragment();
                // Itemの受け渡し
                fragment.setArguments(bundle);
                fragment.show(fragmentManager, "show category fragment");

                break;
            case TransitionEvent.BACK_CATEGORY_SELECT_TO_REGISTER_FLAG:
                setResult(RESULT_OK);
                finish();
            default:
                break;
        }
    }
}
