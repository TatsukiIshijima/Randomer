package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MainGenerator {

    public static void main(String[] args)  throws Exception {

        // DBバージョンと出力先のパッケージ名
        Schema schema = new Schema(1,"com.io.tatsuki.randomer.Repositories.db");
        addItemEntity(schema);
        // Dao生成
        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    /**
     * Itemテーブル追加
     * @param schema
     */
    public static void addItemEntity(Schema schema) {
        // テーブル名
        Entity entity = schema.addEntity("Item");
        entity.addIdProperty().autoincrement();
        entity.addStringProperty("category").notNull();
        entity.addStringProperty("title").notNull();
        entity.addStringProperty("usetId").notNull();
        entity.addStringProperty("password").notNull();
        entity.addStringProperty("url");
        entity.addStringProperty("imagePath").notNull();
    }
}
