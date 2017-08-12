package com.io.tatsuki.randomer.Events;

import lombok.Getter;

/**
 * 画面遷移用のEvent
 */

public class TransitionEvent {

    public static final int TRANS_TO_REGISTER_FLAG = 1;         // 登録画面遷移用フラグ

    @Getter
    private int transitionFlag;

    public TransitionEvent(int flag) {
        this.transitionFlag = flag;
    }
}
