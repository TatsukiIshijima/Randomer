package com.io.tatsuki.randomer.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.io.tatsuki.randomer.Activities.RegisterActivity;
import com.io.tatsuki.randomer.Adapters.CategoryItemAdapter;
import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.Utils.EncryptUtil;
import com.io.tatsuki.randomer.Utils.ImageUtil;
import com.io.tatsuki.randomer.ViewModels.RegisterViewModel;

import org.greenrobot.eventbus.EventBus;

import static com.io.tatsuki.randomer.Activities.RegisterActivity.ITEM_KEY;

/**
 * カテゴリーアイコン選択 Fragment
 */

public class CategorySelectFragment extends DialogFragment {

    private static final String TAG = CategorySelectFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private RegisterViewModel registerViewModel;
    private int mFlag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        registerViewModel = new RegisterViewModel(getActivity());
        final Item item = getItem();

        alertDialog = builder.create();
        alertDialog.setTitle("アイコン選択");

        // カスタムレイアウト
        View view = getActivity().getLayoutInflater().inflate(R.layout.alert_select_category, null);
        GridView gridView = (GridView) view.findViewById(R.id.alert_select_category_grid_view);
        CategoryItemAdapter adapter = new CategoryItemAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick : " + i);
                // ImagePathの取得
                item.setImagePath(ImageUtil.convertUrlFromDrawableResId(getActivity(), CategoryItemAdapter.mResourceList[i]));
                // パスワードの暗号化
                String encryptPass = EncryptUtil.encryptRSA(getActivity(), item.getPassword());
                item.setPassword(encryptPass);
                Log.d(TAG, "EncryptPassword : " + encryptPass);

                // 詳細画面からであれば更新
                if (mFlag == 1) {
                    // 更新
                    Log.d(TAG, "update : " + item.getId());
                    registerViewModel.update(item);
                    // RegisterActivityに通知し、スタッククリアと更新したItemの送信
                    EventBus.getDefault().post(new TransitionEvent(TransitionEvent.BACK_CATEGORY_SELECT_TO_REGISTER_FLAG, item));
                }
                // ホーム画面からであれば新規登録
                else {
                    // 保存の実行
                    Log.d(TAG, "save : " + item.getId());
                    registerViewModel.save(item);
                    // RegisterActivityに通知し、ホーム画面に戻る
                    EventBus.getDefault().post(new TransitionEvent(TransitionEvent.BACK_CATEGORY_SELECT_TO_REGISTER_FLAG));
                }
                alertDialog.cancel();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();

        return alertDialog;
    }

    /**
     * 受け渡しされたItemを受け取る
     * @return  item
     */
    private Item getItem() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Item item = (Item) bundle.getSerializable(ITEM_KEY);
            // 遷移元のフラグを受け取る
            mFlag = bundle.getInt(RegisterActivity.EDIT_OR_SAVE_FLAG);
            return item;
        } else {
            return null;
        }
    }
}
