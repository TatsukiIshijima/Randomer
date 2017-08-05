package com.io.tatsuki.randomer.ViewModels;

import android.util.Log;
import android.view.View;

import com.io.tatsuki.randomer.R;

/**
 * ホーム画面のViewModel
 */

public class HomeViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    /**
     * ボタンタップイベント
     * @param view
     */
    public void onClickButton(View view) {
        switch (view.getId()) {
            // Floating Action Button
            case R.id.activity_home_fb:
                Log.d(TAG, "Floating Action Button Clicked");
                break;
            default:
                break;
        }
    }
}
