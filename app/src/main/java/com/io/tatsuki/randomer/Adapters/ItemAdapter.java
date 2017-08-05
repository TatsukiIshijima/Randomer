package com.io.tatsuki.randomer.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.ViewModels.ItemViewModel;

/**
 * ホーム画面のリストアダプター
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * ViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ItemViewModel itemViewModel;

        public ItemViewHolder(View itemView, ItemViewModel itemViewModel) {
            super(itemView);
            this.itemViewModel = itemViewModel;
        }

        /**
         * モデルを読み込みタイトルの設定
         * @param item
         */
        public void loadModel(Item item) {
            itemViewModel.setItemTitle(item);
        }
    }
}
