package com.io.tatsuki.randomer.Repositories.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ITEM".
*/
public class ItemDao extends AbstractDao<Item, Long> {

    public static final String TABLENAME = "ITEM";

    /**
     * Properties of entity Item.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Category = new Property(1, String.class, "category", false, "CATEGORY");
        public final static Property Title = new Property(2, String.class, "title", false, "TITLE");
        public final static Property UsetId = new Property(3, String.class, "usetId", false, "USET_ID");
        public final static Property Password = new Property(4, String.class, "password", false, "PASSWORD");
        public final static Property Url = new Property(5, String.class, "url", false, "URL");
        public final static Property ImagePath = new Property(6, String.class, "imagePath", false, "IMAGE_PATH");
    }


    public ItemDao(DaoConfig config) {
        super(config);
    }
    
    public ItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"CATEGORY\" TEXT NOT NULL ," + // 1: category
                "\"TITLE\" TEXT NOT NULL ," + // 2: title
                "\"USET_ID\" TEXT NOT NULL ," + // 3: usetId
                "\"PASSWORD\" TEXT NOT NULL ," + // 4: password
                "\"URL\" TEXT," + // 5: url
                "\"IMAGE_PATH\" TEXT NOT NULL );"); // 6: imagePath
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Item entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCategory());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getUsetId());
        stmt.bindString(5, entity.getPassword());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(6, url);
        }
        stmt.bindString(7, entity.getImagePath());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Item entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getCategory());
        stmt.bindString(3, entity.getTitle());
        stmt.bindString(4, entity.getUsetId());
        stmt.bindString(5, entity.getPassword());
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(6, url);
        }
        stmt.bindString(7, entity.getImagePath());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Item readEntity(Cursor cursor, int offset) {
        Item entity = new Item( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // category
            cursor.getString(offset + 2), // title
            cursor.getString(offset + 3), // usetId
            cursor.getString(offset + 4), // password
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // url
            cursor.getString(offset + 6) // imagePath
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Item entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCategory(cursor.getString(offset + 1));
        entity.setTitle(cursor.getString(offset + 2));
        entity.setUsetId(cursor.getString(offset + 3));
        entity.setPassword(cursor.getString(offset + 4));
        entity.setUrl(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImagePath(cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Item entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Item entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Item entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
