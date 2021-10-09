package com.joey.checkchecker.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast mToast;

    public static void showMeg(Context context, String msg, int duration){
        if(mToast != null){
            mToast.setText(msg);
            mToast.setDuration(duration);
        }else{
            mToast = Toast.makeText(context, msg, duration);
        }
        mToast.show();
    }
}
