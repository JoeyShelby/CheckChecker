package com.joey.checkchecker.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.joey.checkchecker.R;
import com.joey.checkchecker.dataBase.CheckItemDataBase;
import com.joey.checkchecker.pojo.CheckItemEntity;
import com.joey.checkchecker.service.OperateData;
import com.joey.checkchecker.utils.DateUtil;
import com.joey.checkchecker.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 供打卡页面RecyclerView使用的Adapter
 */
public class CheckAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    CheckItemDataBase db ;
    List<CheckItemEntity> items = new ArrayList<CheckItemEntity>();

    public CheckAdapter(Context mContext, CheckItemDataBase db) {
        this.mContext = mContext;
        this.db = db;
    }

    public void initialize(){
        items = OperateData.queryAllItems(db.getCheckItemDao());
    }

    //添加新项目
    @SuppressLint("NotifyDataSetChanged")
    public void addItem(CheckItemEntity item){
        if(!OperateData.existName(db.getCheckItemDao(), item.getName())){
            OperateData.addItem(db.getCheckItemDao(), item);
            //维护脆弱的list/(ㄒoㄒ)/~~
            initialize();
            notifyDataSetChanged();
        }else{
            ToastUtil.showMeg(mContext, "【" + item.getName()+"】已存在", Toast.LENGTH_SHORT);
        }
    }

    //删除项目
    @SuppressLint("NotifyDataSetChanged")
    private void deleteItem(String name){
        if(OperateData.existName(db.getCheckItemDao(), name)){
            OperateData.deleteByName(db.getCheckItemDao(), name);
            //维护脆弱的list/(ㄒoㄒ)/~~
            initialize();
            notifyDataSetChanged();
        }else{
            ToastUtil.showMeg(mContext, "【" + name+"】不存在", Toast.LENGTH_SHORT);
        }
    }

    @NonNull
    @Override
    //ViewHolder承载的是每一个列表项的视图，所以当使用RecyclerView的时候需要先对ViewHolder进行初始化定义。
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_check_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ((ItemViewHolder)holder).mTvItem.setText(items.get(position).getName());
        //√点击打卡事件
        ((ItemViewHolder)holder).mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = OperateData.queryRecentDate(db.getCheckItemDao(), items.get(position).getName());
                Date today = new Date();
                long gapDays = (today.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
                //判断是否能够打卡（每天只能打卡一次）
                if(gapDays < 1){
                    ToastUtil.showMeg(view.getContext(), "【"+items.get(position).getName()+"】今天已经打过卡了！", Toast.LENGTH_SHORT);
                }else{
                    //gapDays <= 1 判断是否为连续打卡
                    OperateData.check(db.getCheckItemDao(), items.get(position).getName(), today, gapDays == 1);
                    ToastUtil.showMeg(view.getContext(), "坚持就是胜利！", Toast.LENGTH_SHORT);
                }

                Log.d("JoeyItem", db.getCheckItemDao().getItemByName(items.get(position).getName()).toString());

            }
        });
        //长按项目【删除】事件
        ((ItemViewHolder)holder).mTvItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("删除");
                builder.setMessage("确定删除【" + items.get(position).getName() + "]");
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(items.get(position).getName());
                        notifyDataSetChanged();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return OperateData.queryAllItems(db.getCheckItemDao()).size();
    }

    //ViewHolder 描述了一个项目视图和关于它在 RecyclerView 中的位置的元数据。
    // 适配器应该随意使用他们自己的自定义 ViewHolder 实现来存储数据，从而使绑定视图内容更容易。
    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mTvItem;
        private Button mBtnCheck;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvItem = itemView.findViewById(R.id.tv_check_item);
            mBtnCheck = itemView.findViewById(R.id.btn_check);
        }
    }

}
