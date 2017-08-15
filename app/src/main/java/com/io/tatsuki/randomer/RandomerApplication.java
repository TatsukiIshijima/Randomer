package com.io.tatsuki.randomer;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.io.tatsuki.randomer.Repositories.db.DaoMaster;
import com.io.tatsuki.randomer.Repositories.db.DaoSession;
import com.io.tatsuki.randomer.Repositories.db.ItemDao;

/**
 * Applicationクラス
 */

public class RandomerApplication extends Application {

    private static final String TAG = RandomerApplication.class.getSimpleName();
    private static final String DATABASE_NAME = "ITEM_DB";

    private ItemDao itemDao;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        itemDao = setupDB();
    }

    /**
     * データベースの初期設定
     * @return ItemDao
     */
    private ItemDao setupDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(database);
        DaoSession session = master.newSession();
        return session.getItemDao();
    }
}
