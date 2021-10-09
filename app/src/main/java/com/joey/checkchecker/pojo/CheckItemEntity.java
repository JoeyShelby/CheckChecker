package com.joey.checkchecker.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * 打卡项目javaBean，每一个打卡项目对应一个CheckItemEntity，即数据库中的一条记录
 */
@Entity
public class CheckItemEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")  //每个打卡项目名称必须不同
    private String name;
    @ColumnInfo(name = "date")
    private String buildDate;
    @ColumnInfo(name = "recentDate") //最近一次打卡的日期
    private String recentDate;
    @ColumnInfo(name = "days")
    private int days;

    public CheckItemEntity(String name, String buildDate, int days) {
        this.name = name;
        this.buildDate = buildDate;
        this.recentDate = "1970/01/01";
        this.days = days;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBuildDate() {
        return buildDate;
    }
    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }
    public String getRecentDate() {
        return recentDate;
    }
    public void setRecentDate(String recentDate) {
        this.recentDate = recentDate;
    }
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "CheckItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", buildDate='" + buildDate + '\'' +
                ", recentDate='" + recentDate + '\'' +
                ", days=" + days +
                '}';
    }
}