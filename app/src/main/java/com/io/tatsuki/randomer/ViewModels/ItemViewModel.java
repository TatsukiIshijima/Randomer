package com.io.tatsuki.randomer.ViewModels;

import android.databinding.ObservableField;

import com.io.tatsuki.randomer.Models.Item;

import java.util.ArrayList;

/**
 * Item View Model
 */
public class ItemViewModel {

    private static final String TAG = ItemViewModel.class.getSimpleName();

    private ArrayList<Item> items;
    public ObservableField<String> title = new ObservableField<>();

    public ItemViewModel(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * リストに表示するタイトルの設定
     * @param item
     */
    public void setItemTitle(Item item) {
        title.set(item.getTitle());
    }
}
