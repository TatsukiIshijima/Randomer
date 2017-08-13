package com.io.tatsuki.randomer.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.io.tatsuki.randomer.R;

import java.util.ArrayList;

/**
 * カテゴリーアイコン選択のための Adapter
 */

public class CategoryItemAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int[] mResourceList = new int[]{ R.drawable.ic_airplane,
            R.drawable.ic_car,
            R.drawable.ic_cloud,
            R.drawable.ic_food,
            R.drawable.ic_internet,
            R.drawable.ic_mail,
            R.drawable.ic_map,
            R.drawable.ic_music,
            R.drawable.ic_pc,
            R.drawable.ic_photo,
            R.drawable.ic_setting,
            R.drawable.ic_share,
            R.drawable.ic_shopping,
            R.drawable.ic_track,
            R.drawable.ic_train,
            R.drawable.ic_other};


    public CategoryItemAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mResourceList.length;
    }

    @Override
    public Object getItem(int position) {
        return mResourceList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.alert_select_category_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.alert_select_category_item_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(mResourceList[position]);
        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
