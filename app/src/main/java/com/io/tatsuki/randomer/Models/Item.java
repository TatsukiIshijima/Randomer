package com.io.tatsuki.randomer.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * アイテム Model
 */

@NoArgsConstructor
public class Item {

    @Setter
    @Getter
    private String category;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    private String userId;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String url;

    @Setter
    @Getter
    private String imagePath;

}
