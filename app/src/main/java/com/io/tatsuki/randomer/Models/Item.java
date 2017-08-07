package com.io.tatsuki.randomer.Models;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * アイテム Model
 */

@NoArgsConstructor
public class Item implements Serializable {

    @Setter
    @Getter
    private String mCategory;

    @Setter
    @Getter
    private String mTitle;

    @Setter
    @Getter
    private String mUserId;

    @Setter
    @Getter
    private String mPassword;

    @Setter
    @Getter
    private String mUrl;

    @Setter
    @Getter
    private String mImagePath;

}