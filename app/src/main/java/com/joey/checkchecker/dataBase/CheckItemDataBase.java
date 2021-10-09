package com.joey.checkchecker.dataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.joey.checkchecker.dao.CheckItemDao;
import com.joey.checkchecker.pojo.CheckItemEntity;

/**
 * 依据CheckItemEntity实体创建的数据库
 */
@Database(entities = {CheckItemEntity.class}, version = 2,exportSchema = false)
public abstract class CheckItemDataBase extends RoomDatabase {
        public static CheckItemDataBase INSTANCE;

        public abstract CheckItemDao getCheckItemDao();

        public static synchronized CheckItemDataBase getCheckItemDataBase(Context context){
                if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CheckItemDataBase.class, "CheckItemDataBase")
                                .addMigrations(MIGRATION_1_2)
                                .allowMainThreadQueries()
                                .build();
                }
                return INSTANCE;
        }

        static final Migration MIGRATION_1_2 = new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE CheckItemEntity ADD COLUMN recentDate TEXT NOT NULL DEFAULT '1970/01/01'");
                }
        };
}