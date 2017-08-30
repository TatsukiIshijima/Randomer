package com.io.tatsuki.randomer.Events;

import lombok.Getter;

/**
 * ボタンのイベント
 */

public class ButtonEvent {

    public static final int generateButtonFlag = 0;       // 生成ボタンフラグ
    public static final int saveButtonFlag     = 1;       // 保存ボタンフラグ
    public static final int addButtonFlag      = 2;       // 追加ボタンフラグ

    @Getter
    private int buttonFlag;
    @Getter
    private boolean buttonState;

    public ButtonEvent(int flag) {
        this.buttonFlag = flag;
    }

    public ButtonEvent(int flag, boolean state) {
        this.buttonFlag = flag;
        this.buttonState = state;
    }
}
