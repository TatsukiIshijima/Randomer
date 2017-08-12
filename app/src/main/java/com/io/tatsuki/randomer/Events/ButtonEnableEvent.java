package com.io.tatsuki.randomer.Events;

import lombok.Getter;

/**
 * ボタンの活性・非活性通知のイベント
 */

public class ButtonEnableEvent {

    @Getter
    private boolean buttonFlag;

    public ButtonEnableEvent(boolean flag) {
        this.buttonFlag = flag;
    }
}
