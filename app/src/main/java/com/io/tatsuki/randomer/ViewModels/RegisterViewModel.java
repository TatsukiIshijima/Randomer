package com.io.tatsuki.randomer.ViewModels;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.io.tatsuki.randomer.Events.ButtonEvent;
import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.LocalAccess;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.Utils.EncryptUtil;
import com.io.tatsuki.randomer.Utils.GenerateUtil;
import com.io.tatsuki.randomer.Utils.KeyboardUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    private String mId;           // プライマリーキー

    @Setter
    private int mFlag = 0;

    @Getter
    private List<String> mCategoryList;
    private Context mContext;
    private LocalAccess mLocalAccess;

    /**
     * コンストラクタ
     * @param context
     */
    public RegisterViewModel(Context context) {
        this.mContext = context;
        mLocalAccess = new LocalAccess(context);
        mLocalAccess.setupDB();
    }

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
        mPassword.set(EncryptUtil.decryptRSA(mContext, password));
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
     * DBからカテゴリーを読み込む
     */
    public void loadCategoryList() {
        mCategoryList = mLocalAccess.fetchCategoryList();
    }

    /**
     * カテゴリーをArrayListに追加
     * @param category
     */
    public void addCategory(String category) {
        mCategoryList.add(category);
    }

    /**
     * 編集時に登録されているカテゴリーの位置を取得
     * @param category
     * @return position
     */
    public int getCategoryPosition(String category) {
        int position = -1;
        for (int i=0; i<mCategoryList.size(); i++) {
            if (mCategoryList.get(i).equals(category)) {
                position = i;
            }
        }
        return position;
    }

    /**
     * 生成ボタンの活性・非活性の切り替え
     */
    private void setGenerateButtonState() {
        if (mNumberToggle.get() | mUpperToggle.get() | mLowerToggle.get() | mSymbolToggle.get()) {
            EventBus.getDefault().post(new ButtonEvent(ButtonEvent.generateButtonFlag, true));
        } else {
            EventBus.getDefault().post(new ButtonEvent(ButtonEvent.generateButtonFlag, false));
        }
    }

    /**
     * 保存ボタンの活性・非活性の切り替え
     */
    private void setSaveButtonState() {
        if (mCategory.get() != null && mTitle.get() != null && mUserId.get() != null && mPassword.get() != null) {
            if (!mCategory.get().isEmpty() && mTitle.get().length() > 0 && mUserId.get().length() > 0 && mPassword.get().length() > 0) {
                EventBus.getDefault().post(new ButtonEvent(ButtonEvent.saveButtonFlag, true));
            } else {
                EventBus.getDefault().post(new ButtonEvent(ButtonEvent.saveButtonFlag, false));
            }
        } else {
            // 詳細画面からの遷移の場合、ボタンを活性状態に変更
            if (mFlag == 1) {
                EventBus.getDefault().post(new ButtonEvent(ButtonEvent.saveButtonFlag, true));
            } else {
                EventBus.getDefault().post(new ButtonEvent(ButtonEvent.saveButtonFlag, false));
            }
        }
    }

    /**
     * ボタンイベント
     * @param view
     */
    public void onClickButton(View view) {
        switch (view.getId()) {
            //  カテゴリー追加ボタン
            case R.id.activity_register_add_button:
                Log.d(TAG, "Add Button Clicked");
                EventBus.getDefault().post(new ButtonEvent(ButtonEvent.addButtonFlag));
                break;
            // 生成ボタン
            case R.id.activity_register_generate_button:
                Log.d(TAG, "Generate Button Clicked");
                generate();
                break;
            // 保存ボタン
            case R.id.activity_register_save_button:
                Log.d(TAG, "Save Button Clicked");
                Item item;
                if (getMId() != null) {
                    // 編集用
                    item = new Item(Long.parseLong(getMId()), mCategory.get(), mTitle.get(), mUserId.get(), mPassword.get(), mUrl.get(), null);
                } else {
                    // 新規登録用
                    item = new Item(null, mCategory.get(), mTitle.get(), mUserId.get(), mPassword.get(), mUrl.get(), null);
                }
                // イベントにItemも渡して通知
                EventBus.getDefault().post(new TransitionEvent(TransitionEvent.TRANS_TO_HOME_FLAG, item));
                break;
            // 数字トグルボタン
            case R.id.activity_register_number_toggle:
                Log.d(TAG, "Number Toggle Clicked");
                setGenerateButtonState();
                break;
            // 大文字トグルボタン
            case R.id.activity_register_upper_toggle:
                Log.d(TAG, "Upper Toggle Clicked");
                setGenerateButtonState();
                break;
            // 小文字トグルボタン
            case R.id.activity_register_lower_toggle:
                Log.d(TAG, "Lower Toggle Clicked");
                setGenerateButtonState();
                break;
            // 記号トグルボタン
            case R.id.activity_register_symbol_toggle:
                Log.d(TAG, "Symbol Toggle Clicked");
                setGenerateButtonState();
                break;
            default:
                break;
        }
    }

    /**
     * パスワード生成
     */
    private void generate() {
        if (mPasswordLength.get() == 0) {
            return;
        }
        if (mNumberToggle.get() | mUpperToggle.get() | mLowerToggle.get() | mSymbolToggle.get()) {
            String password = GenerateUtil.generatePassword(mPasswordLength.get(),
                                                            mNumberToggle.get(),
                                                            mUpperToggle.get(),
                                                            mLowerToggle.get(),
                                                            mSymbolToggle.get());
            mPassword.set(password);
        } else {
            return;
        }
    }

    /**
     * 保存
     * @param item
     */
    public void save(Item item) {
        mLocalAccess.save(item);
    }

    /**
     * 更新
     * @param item
     */
    public void update(Item item) {
        mLocalAccess.update(item);
    }

    /**
     * キーイベント
     * @return
     */
    public View.OnKeyListener onKeyListener() {
        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                switch (view.getId()) {
                    case R.id.activity_register_password_edit:
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            //キーボードを閉じる
                            KeyboardUtil.hideKeyboard(mContext, view);
                            return true;
                        }
                        break;
                }
                return false;
            }
        };
        return keyListener;
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
                Log.d(TAG, "beforeTextChanged");
            }
            // 変更中
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Log.d(TAG, "onTextChanged");
                setSaveButtonState();
            }
            // 変更後
            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "afterTextChanged");
                setSaveButtonState();
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

    /**
     * Spinnerのイベント
     */
    public Spinner.OnItemSelectedListener spinnerItemSelected() {
        Spinner.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                String category = (String) spinner.getSelectedItem();
                mCategory.set(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        return itemSelectedListener;
    }
}
