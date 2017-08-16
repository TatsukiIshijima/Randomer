package com.io.tatsuki.randomer.Events;


import com.io.tatsuki.randomer.Repositories.db.Item;

import lombok.Getter;

/**
 * 画面遷移用のEvent
 */

public class TransitionEvent {

    public static final int TRANS_TO_REGISTER_FLAG                = 1;         // 登録画面遷移用フラグ
    public static final int TRANS_TO_DETAIL_FLAG                  = 2;         // 詳細画面遷移用フラグ
    public static final int TRANS_TO_HOME_FLAG                    = 3;         // ホーム画面遷移用フラグ
    public static final int BACK_CATEGORY_SELECT_TO_REGISTER_FLAG = 4;         // カテゴリーアイコン選択から登録画面に戻る用のフラグ

    @Getter
    private int transitionFlag;
    @Getter
    private Item item;

    public TransitionEvent(int flag) {
        this.transitionFlag = flag;
    }

    public TransitionEvent(int flag, Item item) {
        this.transitionFlag = flag;
        this.item = item;
    }
}
