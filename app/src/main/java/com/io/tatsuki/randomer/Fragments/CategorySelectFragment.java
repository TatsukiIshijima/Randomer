package com.io.tatsuki.randomer.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.io.tatsuki.randomer.R;

/**
 * カテゴリーアイコン選択 Fragment
 */

public class CategorySelectFragment extends DialogFragment {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());

        alertDialog = builder.create();
        alertDialog.setTitle("アイコン選択");

        // カスタムレイアウト
        View view = getActivity().getLayoutInflater().inflate(R.layout.alert_select_category, null);
        alertDialog.setView(view);
        alertDialog.show();

        return alertDialog;
    }
}
