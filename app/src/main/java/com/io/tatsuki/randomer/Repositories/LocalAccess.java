package com.io.tatsuki.randomer.Repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.io.tatsuki.randomer.Repositories.db.DaoMaster;
import com.io.tatsuki.randomer.Repositories.db.DaoSession;
import com.io.tatsuki.randomer.Repositories.db.Item;
import com.io.tatsuki.randomer.Repositories.db.ItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * ローカルアクセスクラス
 */

public class LocalAccess {

    private static final String DATABASE_NAME = "ITEM_DB";
    private Context mContext;
    private ItemDao mItemDao;

    public LocalAccess(Context context) {
        this.mContext = context;
    }

    /**
     * データベースの初期設定
     * @return ItemDao
     */
    public void setupDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(database);
        DaoSession session = master.newSession();
        mItemDao = session.getItemDao();
    }

    /**
     * データの保存
     * @param item
     */
    public void save(Item item) {
        mItemDao.insert(item);
    }

    /**
     * 全アイテムの取得
     * @return itemList
     */
    public ArrayList<com.io.tatsuki.randomer.Models.Item> fetchItemList() {
        ArrayList<com.io.tatsuki.randomer.Models.Item> itemArrayList = new ArrayList<>();
        List<Item> itemList = mItemDao.loadAll();
        for (Item item : itemList) {
            com.io.tatsuki.randomer.Models.Item itemModel = new com.io.tatsuki.randomer.Models.Item(
                    item.getCategory(),
                    item.getTitle(),
                    item.getUsetId(),
                    item.getPassword(),
                    item.getUrl(),
                    item.getImagePath());
        }
        return itemArrayList;
    }

    /**
     * カテゴリーリストの取得
     * @return
     */
    public ArrayList<String> fetchCategoryList() {
        ArrayList<String> categories = new ArrayList<>();
        List<Item> categoryList = mItemDao.queryBuilder().orderDesc(ItemDao.Properties.Category).build().list();
        for (Item categoryItem : categoryList) {
            categories.add(categoryItem.getCategory());
        }
        return categories;
    }
}
