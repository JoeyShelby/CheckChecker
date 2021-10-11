package com.joey.checkchecker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joey.checkchecker.R;
import com.joey.checkchecker.dataBase.CheckItemDataBase;
import com.joey.checkchecker.pojo.CheckItemEntity;
import com.joey.checkchecker.service.OperateData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CountAdapter extends RecyclerView.Adapter {
    private Context mContext;
    CheckItemDataBase db ;
    List<CheckItemEntity> items = new ArrayList<CheckItemEntity>();

    public CountAdapter(Context mContext, CheckItemDataBase db) {
        this.mContext = mContext;
        this.db = db;
        initialize();
    }

    public void initialize(){
        items = OperateData.queryAllItems(db.getCheckItemDao());

        //检查项目是否已经断签，若已断签，则需要初始化其打卡日期
        Date today = new Date();
        for(CheckItemEntity checkItemEntity : items){
            Date date = OperateData.queryRecentDate(db.getCheckItemDao(), checkItemEntity.getName());
            long gapDays = (today.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);

            if(gapDays > 1){
                OperateData.breakCheck(db.getCheckItemDao(), checkItemEntity.getName());
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_count_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).mTvCount.setText("【"+items.get(position).getName()+"】连续打卡"+items.get(position).getDays()+"天");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvCount;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvCount = itemView.findViewById(R.id.tv_count_item);
        }
    }
}
