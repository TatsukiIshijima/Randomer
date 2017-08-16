package com.io.tatsuki.randomer.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.io.tatsuki.randomer.Adapters.CategoryItemAdapter;
import com.io.tatsuki.randomer.Events.TransitionEvent;

import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.Repositories.db.Item;
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
                item.setImagePath("path" + i);
                // 保存の実行
                registerViewModel.save(item);
                // RegisterActivityに通知
                EventBus.getDefault().post(new TransitionEvent(TransitionEvent.BACK_CATEGORY_SELECT_TO_REGISTER_FLAG));
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
            return item;
        } else {
            return null;
        }
    }
}
