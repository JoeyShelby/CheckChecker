package com.joey.checkchecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joey.checkchecker.fragment.CheckFragment;
import com.joey.checkchecker.fragment.CountFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBnv ;
    private Fragment mFragmentMain;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置默认fragment
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_main, new CheckFragment());
        transaction.commit();

        mFragmentMain = new CheckFragment();
        mBnv = findViewById(R.id.bnv_main);

        //主页面下方的导航菜单的【切换】点击事件
        mBnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.item_check:
                        transaction.replace(R.id.fragment_main, new CheckFragment());
                        transaction.commit();
                        return true;
                    case R.id.item_count:
                        transaction.replace(R.id.fragment_main, new CountFragment());
                        transaction.commit();
                        return true;
                }
                return false;
            }
        });
    }
}