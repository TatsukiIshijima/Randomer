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

    public Item(String category, String title, String userId, String password, String url, String imagePath) {
        this.mCategory = category;
        this.mTitle = title;
        this.mUserId = userId;
        this.mPassword = password;
        this.mUrl = url;
        this.mImagePath = imagePath;
    }

}
