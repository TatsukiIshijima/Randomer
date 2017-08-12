package com.io.tatsuki.randomer.Utils;

import android.content.Context;
import android.hardware.input.InputManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * キーボードに関するUtilクラス
 */

public class KeyboardUtil {

    /**
     * キーボードを隠す
     * @param context
     * @param view
     */
    public static void hideKeyboard(@NonNull Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
