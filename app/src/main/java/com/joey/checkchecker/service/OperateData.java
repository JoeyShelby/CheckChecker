package com.joey.checkchecker.service;


import com.joey.checkchecker.dao.CheckItemDao;
import com.joey.checkchecker.pojo.CheckItemEntity;
import com.joey.checkchecker.utils.DateUtil;

import java.util.Date;
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
    //根据项目名称，返回最近的打卡日期
    public static Date queryRecentDate(CheckItemDao checkItemDao,String name){
        CheckItemEntity item = checkItemDao.getItemByName(name);
        String recentDate = item.getRecentDate();
        return DateUtil.stringToDate(recentDate);
    }
    //打卡成功，修改项目的最近打卡日期和连续打卡日期
    public static void check(CheckItemDao checkItemDao,String name,Date recentDate,boolean gap){
        CheckItemEntity item = checkItemDao.getItemByName(name);
        item.setRecentDate(DateUtil.formatDate(recentDate));
        if(gap){
            item.setDays(item.getDays()+1);
        }else{
            item.setDays(1);
        }
        checkItemDao.updateItem(item);
    }
    //断签咯，重开吧孩子
    public static void breakCheck(CheckItemDao checkItemDao,String name){
        CheckItemEntity item = checkItemDao.getItemByName(name);
        item.setRecentDate("1970/01/01");
        item.setDays(0);

        checkItemDao.updateItem(item);
    }
}
