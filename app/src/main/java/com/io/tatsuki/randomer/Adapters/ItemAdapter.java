package com.io.tatsuki.randomer.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.io.tatsuki.randomer.Models.Item;
import com.io.tatsuki.randomer.R;
import com.io.tatsuki.randomer.ViewModels.ItemViewModel;
import com.io.tatsuki.randomer.databinding.ItemBinding;

import java.util.ArrayList;

/**
 * ホーム画面のリストアダプター
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private static final String TAG = ItemAdapter.class.getSimpleName();
    private Context mContext;
    private ItemBinding mBinding;
    private ArrayList<Item> mItems;

    /**
     * コンストラクタ
     * @param context
     * @param items
     */
    public ItemAdapter(Context context , ArrayList<Item> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item, parent, false);
        mBinding.setItemViewModel(new ItemViewModel());
        return new ItemViewHolder(mBinding.getRoot(), mBinding.getItemViewModel());
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = getItemAt(holder.getLayoutPosition());
        holder.loadModel(item);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        } else {
            return mItems.size();
        }
    }

    private Item getItemAt(int position) {
        return mItems.get(position);
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
