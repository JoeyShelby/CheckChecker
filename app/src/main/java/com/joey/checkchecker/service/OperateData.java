package com.joey.checkchecker.service;


import com.joey.checkchecker.dao.CheckItemDao;
import com.joey.checkchecker.pojo.CheckItemEntity;

import java.util.List;

/**
 * 基于dao中提供的基本方法建立的具体的操作数据的方法
 *返回从数据库中查到的数据，删除指定的数据，添加新项目，查看将要插入的项目是否已经存在于数据库中
 */
public class OperateData {
    //返回所有的项目
    public static List<CheckItemEntity> queryAllItems(CheckItemDao checkItemDao){
        return checkItemDao.getAllItems();
    }
    //根据项目名称删除项目
    public static void deleteByName(CheckItemDao checkItemDao, String name){
         checkItemDao.deleteItem(checkItemDao.getItemByName(name));
    }
    // 根据项目名称检查项目是否已经存在
    //已经存在返回true
    public static boolean existName(CheckItemDao checkItemDao,String name){
        return checkItemDao.getItemByName(name) != null;
    }
    //添加新项目
    public static void addItem(CheckItemDao checkItemDao, CheckItemEntity checkItem){
        checkItemDao.insertItem(checkItem);
    }
}
