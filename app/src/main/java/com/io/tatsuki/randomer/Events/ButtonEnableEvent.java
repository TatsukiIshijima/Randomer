package com.io.tatsuki.randomer.Events;

import lombok.Getter;

/**
 * ボタンの活性・非活性通知のイベント
 */

public class ButtonEnableEvent {

    public static final int generateButtonFlag = 0;       // 生成ボタンフラグ
    public static final int saveButtonFlag     = 1;       // 保存ボタンフラグ

    @Getter
    private int buttonFlag;
    @Getter
    private boolean buttonState;

    public ButtonEnableEvent(int flag, boolean state) {
        this.buttonFlag = flag;
        this.buttonState = state;
    }
}
