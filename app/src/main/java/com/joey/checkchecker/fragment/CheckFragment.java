package com.joey.checkchecker.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joey.checkchecker.R;
import com.joey.checkchecker.adapter.CheckAdapter;
import com.joey.checkchecker.dataBase.CheckItemDataBase;
import com.joey.checkchecker.pojo.CheckItemEntity;
import com.joey.checkchecker.utils.DateUtil;
import com.joey.checkchecker.widget.MyDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 打卡页面
 */
public class CheckFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFAB;    //添加按钮
    private EditText mEtAdd;
    private TextView mTvBlank;

    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.rv_check);
        mFAB = view.findViewById(R.id.fab_main);
        mTvBlank = view.findViewById(R.id.tv_blank);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        CheckAdapter checkAdapter = new CheckAdapter(view.getContext(),CheckItemDataBase.getCheckItemDataBase(view.getContext()));

        //RecyclerView初始化
        checkAdapter.initialize();
        mRecyclerView.setAdapter(checkAdapter);
        //隐藏提示语
        if (checkAdapter.getItemCount() != 0){
            mTvBlank.setVisibility(View.INVISIBLE);
        }

        //悬浮添加按钮的单击事件
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog myDialog = new MyDialog(view.getContext());
                myDialog.setTitle("new");
                //按下对话框的取消
                myDialog.setCancel("罢", new MyDialog.OnCancelListener() {
                    @Override
                    public void onCancel(MyDialog customDialog) {
                        customDialog.dismiss();
                    }
                });
                //按下对话框的确定
                myDialog.setConfirm("就这样", new MyDialog.OnConfirmListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onConfirm(MyDialog customDialog) {
                        mEtAdd = customDialog.getEtAdd();
                        //项目名称不能为空
                        if(mEtAdd.getText().toString().length() != 0){
                            String newItem = mEtAdd.getText().toString();
                            //新打卡项目对象
                            CheckItemEntity checkItemEntity = new CheckItemEntity(newItem,DateUtil.formatDate(new Date()),0);
                            //添加新项目
                            checkAdapter.addItem(checkItemEntity);
                            if (checkAdapter.getItemCount() != 0){
                                mTvBlank.setVisibility(View.INVISIBLE);
                            }
                            customDialog.dismiss();
                        }
                    }
                });
                myDialog.setCanceledOnTouchOutside(false);
                myDialog.show();
            }
        });

    }
}