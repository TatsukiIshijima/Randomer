package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableField;


import com.io.tatsuki.randomer.Repositories.db.Item;

import java.util.List;

import lombok.NoArgsConstructor;

/**
 * Item View Model
 */
@NoArgsConstructor
public class ItemViewModel {

    private static final String TAG = ItemViewModel.class.getSimpleName();

    private List<Item> itemList;
    public ObservableField<String> title = new ObservableField<>();

    public ItemViewModel(List<Item> items) {
        this.itemList = items;
    }

    /**
     * リストに表示するタイトルの設定
     * @param item
     */
    public void setItemTitle(Item item) {
        title.set(item.getTitle());
    }
}
