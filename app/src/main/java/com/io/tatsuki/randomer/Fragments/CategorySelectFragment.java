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
import com.io.tatsuki.randomer.R;

/**
 * カテゴリーアイコン選択 Fragment
 */

public class CategorySelectFragment extends DialogFragment {

    private static final String TAG = CategorySelectFragment.class.getSimpleName();
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

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
            }
        });

        alertDialog.setView(view);
        alertDialog.show();

        return alertDialog;
    }
}
