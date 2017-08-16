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
     * データの削除
     * @param item
     */
    public void delete(Item item) {
        mItemDao.delete(item);
    }

    /**
     * データの編集
     * @param item
     */
    public void edit(Item item) {
        mItemDao.update(item);
    }

    /**
     * 全アイテムの取得
     * @return itemList
     */
    public List<Item> fetchItemList() {
        List<Item> itemList = mItemDao.loadAll();
        return itemList;
    }

    /**
     * カテゴリーリストの取得
     * @return categoryList
     */
    public List<String> fetchCategoryList() {
        List<String> categoryList = new ArrayList<>();
        List<Item> categoryItemList = mItemDao.queryBuilder().orderDesc(ItemDao.Properties.Category).list();
        // TODO:重複を外す
        for(Item item : categoryItemList) {
            categoryList.add(item.getCategory());
        }
        return categoryList;
    }
}
