package com.joey.checkchecker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joey.checkchecker.pojo.CheckItemEntity;

import java.util.List;

/**
 * 数据库访问对象，提供基本的对数据库的操作方法
 */
@Dao
public interface CheckItemDao {
    @Insert
    void insertItem(CheckItemEntity... checkItemEntities);

    @Update
    void updateItem(CheckItemEntity... checkItemEntities);

    @Delete
    void deleteItem(CheckItemEntity... checkItemEntities);

    @Query("DELETE FROM CHECKITEMENTITY")
    void deleteAllItems();

    @Query("SELECT * FROM CHECKITEMENTITY")
    List<CheckItemEntity> getAllItems();

    @Query("SELECT * FROM checkitementity WHERE ID =:id")
    CheckItemEntity getItemById(int id);

    @Query("SELECT * FROM checkitementity WHERE name =:name")
    CheckItemEntity getItemByName(String name);

}
